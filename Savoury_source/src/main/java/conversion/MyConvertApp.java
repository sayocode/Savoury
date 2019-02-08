package main.java.conversion;

import java.io.IOException;
import main.java.conversion.dto.ConvertResultDto;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/** パネルに要素を配置していくクラス.
 * @author sayoko
 *  */
public class MyConvertApp extends InformationSettingPanel {

  /** 置換結果表示ラベル. */
  private Label resultLabel;

  /** フォルダテキストボックス. */
  private Text pathText;

  /** フォルダ選択ボタン. */
  private Button pathInputButton;

  /** 置換テ前キストボックス. */
  private Text beforeText;

  /** 置換後テキストボックス. */
  private Text afterText;

  /** 拡張子テキストボックス. */
  private Text extensionText;

  /** 置換ボタン. */
  private Button convertBtn;

  /** パネル作成. */
  public void createWidget(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    composite.setBackground(new Color(display, 248, 252, 255));

    // 3列のグリッドレイアウトを作成
    GridLayout layout = new GridLayout(3, false);
    composite.setLayout(layout);

    GridData gridData;

    /* 置換結果表示ラベルの設定
     * 枠なし
     * 3カラム連結
     * 中央寄せ
     *  */
    resultLabel = new Label(composite, SWT.NULL);
    resultLabel.setBackground(new Color(display, 0, 0, 0, 0));
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 3;
    resultLabel.setLayoutData(gridData);
    resultLabel.setAlignment(SWT.CENTER);
    resultLabel.setText("");

    // フォルダラベルの設定
    labelLayout(composite, "フォルダ");

    /* フォルダテキストボックスの設定
     * 枠あり
     *  */
    pathText = new Text(composite, SWT.BORDER);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    pathText.setText("");
    pathText.setLayoutData(gridData);

    /* フォルダ選択ボタン
     * 枠なし
     * 幅45ポイント
     *  */
    GridData buttonWidthSet = new GridData();

    pathInputButton = new Button(composite, SWT.NULL);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    buttonWidthSet.widthHint = 55;
    pathInputButton.setLayoutData(buttonWidthSet);
    pathInputButton.setText("選択");

    // フォルダ選択ボタン押下時の処理
    viewPathSelectDialog();

    /* 置換前、置換後のラベルとボタンの設定  */
    beforeText = inputTextLayput(composite, "置換前", beforeText);
    afterText = inputTextLayput(composite, "置換後", afterText);

    // フォルダラベルの設定
    extensionText = inputTextLayput(composite, "拡張子", extensionText);

    //  説明文の追加
    addExplanatory(composite, "拡張子を入力すると、その拡張子のファイルにのみ処理が実行されます。\r\n拡張子は半角カンマで区切ってください。");

    /* 置換ボタンの設定
     * 枠なし
     * 3カラム連結
     *  */
    convertBtn = new Button(composite, SWT.NULL);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    convertBtn.setLayoutData(gridData);
    gridData.horizontalSpan = 3;
    convertBtn.setText("ファイル名一括置換");

    // 置換ボタン選択時の処理
    convertFileName();
  }

  /** 説明文を追加する
   * @param composite 当アプリのウィジェット
   * @param labelText ラベルに表示するテキスト
   *  */
  private void addExplanatory(Composite composite, String labelText) {
    int[] textColor = { 60, 80, 80 };
    labelLayout(composite, "");
    labelLayout(composite, labelText, textColor, 380);
  }

  /** フォルダ選択ボタン押下時の処理. */
  private void viewPathSelectDialog() {
    pathInputButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {

        // ダイアログのオープン
        Shell folderSelectShell = new Shell(display);
        folderSelectShell.setLayout(new FillLayout());
        DirectoryDialog dialog = new DirectoryDialog(folderSelectShell);
        dialog.setMessage(Constants.Message.URGE_SELECTION);

        // ダイアログで選択したフォルダのパスをフォルダテキストボックスに設定
        String path = dialog.open();
        pathText.setText(path != null ? path : pathText.getText());
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
  }

  /**置換ボタン押下時の処理. */
  private void convertFileName() {
    convertBtn.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent event) {

        // 各テキストボックスの値を取得
        String pathInputText = pathText.getText();
        String beforeInputText = beforeText.getText();
        String afterInputText = afterText.getText();
        String extensionInputText = extensionText.getText();

        // ファイル名変更処理を行う
        ChangeFileName changeFileName = new ChangeFileName();
        ConvertResultDto convertResultDto = new ConvertResultDto();
        try {
          convertResultDto = changeFileName.conversionFileName(pathInputText,
              beforeInputText, afterInputText, extensionInputText);
        } catch (IOException e) {
          e.printStackTrace();
          convertResultDto.setResult(false);
          convertResultDto.setMessage(Constants.Message.ERROR_SOMETHING);
        }

        // 結果の出力
        String message = convertResultDto.getMessage();
        if (convertResultDto.isResult()) {
          resultLabel.setForeground(new Color(display, 30, 70, 200));
          message = message + Integer.toString(convertResultDto.getNumberOfFiles());
        } else {
          resultLabel.setForeground(new Color(display, 255, 96, 100));
        }
        resultLabel.setText(message);
      }

      public void widgetDefaultSelected(SelectionEvent event) {
      }
    });
  }

  /** 置換前、置換後のラベルとボタンの設定.
   * @param composite 当アプリのウィジェット
   * @param labelText ラベルに表示するテキスト
   * @param text テキストボックス
   * @return 設定後のテキストボックス
   */
  private Text inputTextLayput(Composite composite, String labelText, Text text) {
    GridData gridData;

    // ラベルの設定
    labelLayout(composite, labelText);

    /* テキストボックスの設定
     * 枠なし
     * 2カラム連結
     *  */
    text = new Text(composite, SWT.BORDER);
    gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = 2;
    text.setText("");
    text.setLayoutData(gridData);

    return text;
  }

  /**ラベルの設定.
   * @param composite 当アプリのウィジェット
   * @param labelText ラベルに表示するテキスト
   */
  protected void labelLayout(Composite composite, String labelText) {
    int[] textColor = { 0, 0, 0 };
    labelLayout(composite, labelText, textColor, 50);
  }

  /**ラベルの設定.
   * @param composite 当アプリのウィジェット
   * @param labelText ラベルに表示するテキスト
   * @param width 幅
   */
  protected void labelLayout(Composite composite, String labelText, int[] textColor, int width) {
    GridData labelWidthSet = new GridData();

    Label label;
    /* ラベルの設定
     * 枠なし
     * 幅50ポイント
     *  */
    label = new Label(composite, SWT.NULL);
    label.setBackground(new Color(display, 0,0,0,0));
    label.setForeground(new Color(display, textColor[0], textColor[1], textColor[2]));
    labelWidthSet.widthHint = width;
    label.setLayoutData(labelWidthSet);
    label.setText(labelText);
  }
}