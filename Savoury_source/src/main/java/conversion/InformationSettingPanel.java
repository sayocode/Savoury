package main.java.conversion;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/** フォルダ情報入力パネルクラス.
 * @author sayoko
 * */
public class InformationSettingPanel {
  static Display display;

  /** 実行時メインメソッド. 
 * @throws IOException IOException */
  public static void main(String[] args) throws IOException {

    // パネルの作成
    display = new Display();
    Shell shell = new Shell(display, SWT.SHELL_TRIM);
    shell.setSize(530, 250);
    shell.setText("Savoury -ファイル名一括置換-");

    // アイコンを設定
    Image img = new FileLoad().getJarFileImg(display,
            "main/resource/icon.ico", "src/main/resource/icon.ico");
    if (img != null) {
      shell.setImage(img);
    }
    shell.setLayout(new FillLayout());

    // パネルに要素を配置していく
    MyConvertApp myConvertApp = new MyConvertApp();
    myConvertApp.createWidget(shell);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep(); 
      }
    }
    display.dispose(); // リソースの解放
  }

}