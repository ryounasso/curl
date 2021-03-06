/*
 * This Java source file was generated by the Gradle 'init' task.
 */
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package curl;

import org.junit.Test;

import static org.junit.Assert.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.WebSocketHandshakeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppTest {
    public static void main(String[] args) {
        boolean isEnd = false;

        while (isEnd == false) {
            List<String> argList = getArgs();
            System.out.println(argList);
            isEnd = operationProcess(argList, isEnd);
        }
    }

    // コマンドを取得する関数
    public static List<String> getArgs() {
        // コマンドラインの引数オプションを格納するリスト
        List<String> argList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("user$");
            String str = bufferedReader.readLine(); // 1行読み込む
            String[] array = str.split(" "); // スペースで文字列を分解し配列化
            // コマンドリストに要素を追加
            for (int i = 0; i < array.length; i++) {
                argList.add(array[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return argList;
    }

    // コマンドラインから処理内容を取得し, それぞれの関数を呼び出し
    public static boolean operationProcess(List<String> argList, boolean isEnd) {

        if (argList.get(0).equals("curl")) {
            argList.remove(0); // リストからcurlを削除
            String url = SerachUrl(argList);

            if (argList.size() == 0) {
                System.out.println(TrimString(GET(url, 0)));
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-d")
                    && argList.contains("-o") && argList.contains("-v")) {
                System.out.println(TrimString(POSTD(url, getParam(argList), 1)));
                outputText(TrimString(POSTD(url, getParam(argList), 1)), argList);
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-d")
                    && argList.contains("-o")) {
                System.out.println(TrimString(POSTD(url, getParam(argList), 0)));
                outputText(TrimString(POSTD(url, getParam(argList), 0)), argList);
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-d")) {
                System.out.println(TrimString(POSTD(url, getParam(argList), 0)));
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-o")
                    && argList.contains("-v")) {
                System.out.println(TrimString(POST(url, 1)));
                outputText(TrimString(POST(url, 0)), argList);
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-o")) {
                System.out.println(TrimString(POST(url, 0)));
                outputText(TrimString(POST(url, 0)), argList);
            } else if (argList.contains("-X") && argList.contains("POST") && argList.contains("-v")) {
                System.out.println(TrimString(POST(url, 1)));
            } else if (argList.contains("-o")) {
                System.out.println(TrimString(GET(url, 0)));
                outputText(TrimString(GET(url, 0)), argList);
            } else if (argList.contains("-v") && argList.contains("-o")) {
                System.out.println(TrimString(GET(url, 1)));
                outputText(TrimString(GET(url, 1)), argList);
            } else if (argList.contains("-X") && argList.contains("POST")) {
                System.out.println(TrimString(POST(url, 0)));
            } else if (argList.contains("-v")) {
                System.out.println(TrimString(GET(url, 1)));
            }
            // 終わる時の処理
        } else if (argList.get(0).equals("quit")) {
            isEnd = true;
        } else {
            System.out.println("This command not found");
        }
        return isEnd;
    }

    // GETメソッド
    public static String GET(String urltext, int flag) {
        String urlText = urltext;
        HttpURLConnection httpURLConnection = null;
        InputStream inputResult = null;
        BufferedReader bufferedReader = null;
        StringBuilder resultTextBuilder = new StringBuilder();

        try {
            URL url = new URL(urlText);
            // 接続用HttpURLConnectionオブジェクト作成
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            // -vオプションが付与された場合
            if (flag == 1) {
                printHeader(httpURLConnection); // ヘッダーをプリント
            }
            httpURLConnection.connect();

            // 応答されたコードがHTTP_OK(200)なら結果を読み込む
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputResult = httpURLConnection.getInputStream(); // ストリームを取得
                bufferedReader = new BufferedReader(new InputStreamReader(inputResult)); // ストリームから文字列読み取り
                String resultText; // readLine()の際に格納する

                // ストリングビルダーに最後の文字まで格納する
                while ((resultText = bufferedReader.readLine()) != null) {
                    resultTextBuilder.append(resultText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // エラー箇所を表示
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close(); // バッファリーダーを閉じる
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect(); // 接続を切る
                }
            } catch (IOException e) {
                e.printStackTrace(); // エラー箇所を表示
            }
        }
        return resultTextBuilder.toString();
    }

    // コマンドライン内から指定されたURLを抽出
    private static String SerachUrl(List<String> argList) {
        String url = ""; // URL

        for (int i = 0; i < argList.size(); i++) {
            // コマンドリストの要素にhttpから始まるものがあるなら
            if (argList.get(i).startsWith("http")) {
                url = argList.get(i); // 要素を取り出しURLに代入
                argList.remove(i); // リストから削除
            }
        }
        System.out.println(url);
        return url;
    }

    // -oコマンド
    private static String oCommand(List<String> argList) {
        String outputFile = "";

        for (int i = 0; i < argList.size(); i++) {
            if (argList.get(i).equals("-o")) {
                outputFile = argList.get(i + 1);
            }
        }
        return outputFile;
    }

    // 形を整える
    public static String TrimString(String resultText) {
        StringBuilder trimedResultText = new StringBuilder(resultText);

        InsertNewLine(trimedResultText, '>');
        InsertNewLine(trimedResultText, ';');
        InsertNewLine(trimedResultText, '{');
        InsertNewLine(trimedResultText, '}');

        return trimedResultText.toString();
    }

    private static void InsertNewLine(StringBuilder resultText, char keyOfNewLine) {
        List<Integer> indexList = new ArrayList<>(); // keyが何番目にあるか格納するリスト
        int insertcount = 0; // 改行コードを挿入した回数

        for (int i = 0; i < resultText.length(); i++) {
            // keyを発見するとリストに追加
            if (resultText.charAt(i) == keyOfNewLine) {
                indexList.add(i);
            }
        }

        for (int j = 0; j < indexList.size(); j++) {
            // keyの後に改行コードを挿入
            resultText.insert(indexList.get(j) + 1 + insertcount, "\n");
            insertcount++; // 挿入回数をインクリメント
        }
    }

    // 指定されたファイルに出力する関数
    public static void outputText(String text, List<String> argList) {
        String outputfile = oCommand(argList);

        try {
            FileWriter fileWriter = new FileWriter(outputfile);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // ヘッダーをプリント
    private static void printHeader(HttpURLConnection httpURLConnection) {
        // ヘッダーとヘッダーの値を取得
        Map headers = httpURLConnection.getHeaderFields();
        Iterator itHeader = headers.keySet().iterator();
        String header = null;
        while (itHeader.hasNext()) {
            String headerKey = (String) itHeader.next();
            header += headerKey + "：" + headers.get(headerKey) + "\r\n";
        }
        System.out.print(header);
    }

    private static String getParam(List<String> argList) {
        String param = ""; // ファイルパラメータを格納

        for (int i = 0; i < argList.size(); i++) {
            if (argList.get(i).equals("-d")) {
                param = argList.get(i + 1);
            }
        }
        // ストリングビルダーに
        StringBuilder newParam = new StringBuilder(param);
        newParam.deleteCharAt(0); // 最初の'を削除
        newParam.deleteCharAt(newParam.length() - 1); // 最後の'を削除
        return newParam.toString();
    }

    // Postメソッド
    public static String POST(String urltext, int flag) {
        String urlText = urltext; // urlを読み込む
        HttpURLConnection httpURLConnection = null;
        InputStream inputResult = null; // 結果（バイト）を格納する変数
        BufferedReader bufferedReader = null; // 結果を読み込み格納する
        StringBuilder resultTextBuilder = new StringBuilder(); // 結果を格納する

        try {
            // try:必ず実行する処理
            URL url = new URL(urlText); // 接続するURLを指定しインスタンス生成
            // コネクションを取得（URLが参照するリモート・オブジェクトへの接続を表すインスタンスを取得）
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST"); // POSTメソッドをセット
            if (flag == 1) {
                printHeader(httpURLConnection);
            }
            httpURLConnection.connect(); // コネクト

            // 応答されたコードがHTTP_OK(200)なら結果を読み込む
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputResult = httpURLConnection.getInputStream(); // ストリームを取得
                bufferedReader = new BufferedReader(new InputStreamReader(inputResult)); // ストリームから文字列読み取り
                String resultText; // readLine()の際に格納する

                // ストリングビルダーに最後の文字まで格納する
                while ((resultText = bufferedReader.readLine()) != null) {
                    resultTextBuilder.append(resultText);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close(); // バッファリーダーを閉じる
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect(); // 接続を切る
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultTextBuilder.toString();
    }

    // POST-dメソッド
    public static String POSTD(String urltext, String params, int flag) {
        String urlText = urltext;
        String param = params;
        System.out.println(param);
        HttpURLConnection httpURLConnection = null;
        InputStream inputResultText = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlText); // 接続するURLを指定したインスタンスを生成
            // コネクションを取得（URLが参照するリモート・オブジェクトへの接続を表すインスタンスを取得）
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true); // 出力を可能にする
            if (flag == 1) {
                printHeader(httpURLConnection);
            }
            httpURLConnection.setRequestMethod("POST"); // POSTメソッドをセット

            // この接続に出力するストリーム
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write(param); // 書き込み
            out.close(); // 書き込み終了

            httpURLConnection.connect(); // コネクト

            // 応答されたコードがHTTP_OK(200)なら結果を読み込む
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputResultText = httpURLConnection.getInputStream(); // ストリームを取得
                bufferedReader = new BufferedReader(new InputStreamReader(inputResultText)); // ストリームを読み取り文字列を読み取る
                String line; // readLine()の際に格納

                // テキストを読み込み
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

            } else { // コードがHTTP_OKじゃないならエラー結果を読み取る
                // エラー表示を読み込み
                inputResultText = httpURLConnection.getErrorStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputResultText));
                String line;

                // テキストを読み込み
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection != null) {
                    // コネクションを切断
                    httpURLConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
