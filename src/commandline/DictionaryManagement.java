package commandline;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;


/**
 *  tham khảo sqlite tại https://www.tutorialspoint.com/sqlite/sqlite_java.htm
*/

public class DictionaryManagement extends Dictionary {

    public DictionaryManagement() {

    }

    /**
     * thêm từ mới vào cây trie.
     *
     * @param english    từ tiếng anh
     * @param description nghĩa tiếng việt.
     */
    public void addNewWord(String english, String description) {

        int idx = 0;
        int charecter;
        for (int i = 0; i < english.length(); i++) {
            trie.get(idx).num++;
            charecter = tranfer(english.charAt(i));
            if (trie.get(idx).child[charecter] == -1) {
                New_node();
                trie.get(idx).child[charecter] = trie.size() - 1;
            }
            idx = trie.get(idx).child[charecter];
        }
        trie.get(idx).num++;
        trie.get(idx).html = description;
        trie.get(idx).endword++;
    }

    /**
     * sửa từ trong cây trie.
     *
     * @param english    từ tiếng anh
     * @param description nghĩa tiếng việt mới.
     */
    public void resetWord(String english, String description) {
        int n = wordToId(english);
        trie.get(n).html = description;
    }

    /**
     * xóa từ khỏi cây trie.
     *
     * @param english từ tiếng anh cần xóa
     */
    public void deleteWord(String english) {
        if (wordToId(english) == 0) return;
        int charecter;
        int idx = 0;
        for (int i = 0; i < english.length(); i++) {
            trie.get(idx).num--;
            charecter = tranfer(english.charAt(i));
            idx = trie.get(idx).child[charecter];
        }
        trie.get(idx).num--;
        trie.get(idx).html = "";
        trie.get(idx).endword--;
    }

    /**
     * lấy danh sách từ điển = dòng lệnh. // không dùng.
     */
    public void insertFromCommandline() {
        int nWords;
        Scanner sc = new Scanner(System.in);
        nWords = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < nWords; ++i) {
            String english = sc.nextLine();
            String vietnamese = sc.nextLine();
            addNewWord(english, vietnamese);
        }
    }

    /**
     * tra cứu một từ từ cây trie.
     *
     * @param englishWord từ tiếng anh cần tra cứu
     * @return nghĩa tiếng việt dưới dạng text/html
     */
    public String dictionaryLookup(String englishWord) {
        int n = wordToId(englishWord);
        if (trie.get(n).html.isEmpty()) {
            return "Can't find this word";
        } else {
            return trie.get(n).html;
        }
    }

    /**
     * lấy dữ liệu từ điển từ file db bằng SQLite, sử dụng thư viện JDBC.
     */
    public void insertFromDatabase() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC"); //tải lớp trình điều khiển
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM av;");
            while (result.next()) {
                String s1 = result.getString("word");
                // Lọc dữ liệu
                boolean ok = true;
                for (int i = 0; i < s1.length(); ++i) {
                    if (s1.charAt(i) - 'a' >= 0 && s1.charAt(i) - 'a' <= 25) continue;
                    if (s1.charAt(i) == ' ') continue;
                    if (s1.charAt(i) == '\'') continue;
                    if (s1.charAt(i) == '-') continue;
                    if (s1.charAt(i) == '.') continue;
                    ok = false;
                    break;
                }
                if (!ok) continue;
                String s2 = result.getString("html");
                addNewWord(s1, s2);
            }
            result.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * thêm từ mới vào file db.
     *
     * @param english    từ tiếng anh
     * @param vietnamese nghĩa tiếng việt (dưới dạng text html)
     */
    public void addToDatabase(String english, String vietnamese) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String query = "SELECT MAX(id) AS LAST FROM av";
            ResultSet rs = stmt.executeQuery(query);
            String maxID = rs.getString("LAST");
            int id = (Integer.parseInt(maxID)) + 1;
            String sql = "INSERT INTO av (id,word,html,description,pronounce) " +
                    "VALUES (" + id + ", '" + english + "', '" + vietnamese + "', 'a', 'a' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * sửa từ đã có trong file db
     *
     * @param english    từ tiếng anh
     * @param vietnamese nghĩa tiếng việt mới (dưới dạng text html)
     */
    public void updateDatabase(String english, String vietnamese) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE av SET html = '" + vietnamese + "' where word LIKE '" + english + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    /**
     * xóa từ khỏi file db.
     *
     * @param english từ cần xóa
     */
    public void removeFromDatabase(String english) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE FROM av where word LIKE '" + english + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

}
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import java.util.Scanner ;
import java.sql.*;

public class DictionaryManagement {
    public Dictionary Dict = new Dictionary();

    // public static class SQLiteJDBC {

    public void insertFromDatabase() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM va;");

            for (int i = 1; i <= 100; i++) {
                String word = rs.getString("word");
                String description = rs.getString("description");
                String pronounce = rs.getString("pronounce");
                String html = rs.getString("html");
                Word word1 = new Word(word, description, pronounce, html);
                Dict.newWord(word1);
                rs.next();
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");

    }

}

 */
