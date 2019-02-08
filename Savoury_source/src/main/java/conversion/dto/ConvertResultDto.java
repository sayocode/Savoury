package main.java.conversion.dto;

/** 変換処理結果クラス.
 * @author sayoko
 * */
public final class ConvertResultDto {

  /** 処理結果.
   */
  private boolean result;

  /** メッセージ.
   */
  private String message;

  /** 変換ファイル数.
   */
  private int numberOfFiles;

  /** 処理結果のゲッター.
   * @return result
   */
  public boolean isResult() {
    return result;
  }

  /** 処理結果のセッター.
   * @param result セットする result
   */
  public void setResult(boolean result) {
    this.result = result;
  }

  /** メッセージのゲッター.
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /** メッセージのセッター.
   * @param message セットする message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /** ファイル数のゲッター.
   * @return numberOfFiles
   */
  public Integer getNumberOfFiles() {
    return numberOfFiles;
  }

  /** ファイル数のセッター.
   * @param numberOfFiles セットする numberOfFiles
   */
  public void setNumberOfFiles(int numberOfFiles) {
    this.numberOfFiles = numberOfFiles;
  }

}