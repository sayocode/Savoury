package main.java.conversion;

/** 定数クラス.
 * @author sayoko
 * */
public final class Constants {
  private Constants() {
  }

  /** メッセージ用定数クラス.
   * @author sayoko
   */
  public static class Message {
    /** エラー：ファイル名を一括置換したいフォルダを選択してください。. */
    public static final String URGE_SELECTION = "ファイル名を一括置換したいフォルダを選択してください。";

    /** エラー：置換が完了しました。. */
    public static final String RESULT_OK = "置換が完了しました。 変換ファイル数：";

    /** エラー：フォルダ、置換前は必須です。. */
    public static final String ERROR_REQUIRED = "フォルダ、置換前は必須です。";

    /** エラー：フォルダが存在しないか、フォルダ名が無効です。. */
    public static final String ERROR_NO_DIRECTORY = "フォルダが存在しないか、フォルダ名が無効です。";

    /** エラー：指定したフォルダにファイルが存在しません。. */
    public static final String ERROR_FILE_NOT_FOUND = "指定したフォルダにファイルが存在しません。";

    /** エラー：条件に合うファイルが存在しませんでした。. */
    public static final String ERROR_FILES_THAT_MEET_THE_CRITERIA = "条件に合うファイルが存在しませんでした。";

    /** エラー：エラーが発生しました。. */
    public static final String ERROR_SOMETHING = "エラーが発生しました。";
  }
}