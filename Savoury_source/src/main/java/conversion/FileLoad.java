package main.java.conversion;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/** 画像取得クラス.
 * @author sayoko
 * */
public class FileLoad {

  private static ClassLoader loader;

  /** 画像取得.
 * @param display ディスプレイ
 * @param path 画像パス
 * @param substitutePath 代替パス
 * @return 画像
 */
  public Image getJarFileImg(Display display, String path, String substitutePath) {

    // jarファイルの場合に画像取得する。
    Image image = getJarFileImg(this, path, display);

    // jarファイルでない場合や、取得できなかった場合は代替パスで画像を取得して返却する。
    return image != null ? image : new Image(display, substitutePath);
  }

 /** jarファイルの場合に画像取得.
 * @param fileLoad 自クラス
 * @param path 画像パス
 * @return
 */
  private Image getJarFileImg(FileLoad fileLoad, String path, Display display) {
    try {
      //ClassLoader から 画像のURL の形式の文字列を作る
      loader = fileLoad.getClass().getClassLoader();
      InputStream inputStream = loader.getResourceAsStream(path);

      boolean isJar = isMatch(fileLoad, path);
      boolean isExe = isExe();

      // jarファイルの場合は画像をInputStreamから取得
      if (isJar) {
        if (inputStream != null) {
          return new Image(Display.getDefault(), inputStream);
        }
      }
      if (isExe) {
        Image exeImage = new Image(display, "icon.ico");
        return exeImage;
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  /**
   * 実行中のファイルがjarファイルがどうかを確認する.
   * @param fileLoad 自クラス
   * @param path 画像パス
   * @return 実行中のファイルがjarファイルかどうか
   * */
  private boolean isMatch(FileLoad fileLoad, String path) {
    final URL url = loader.getResource(path);

    final Pattern p = Pattern.compile("^jar\\:(.+?\\.jar)\\!\\/(.*)");
    final Matcher m = p.matcher(url.toString());
    return m.matches();
  }

  /**
   * 実行中のファイルがexeファイルがどうかを確認する.
   * @return 実行中のファイルがexeファイルかどうか
   * @throws URISyntaxException 
   * */
  private boolean isExe() {
    ProtectionDomain protectionDomain = InformationSettingPanel.class.getProtectionDomain();
    CodeSource codeSource = protectionDomain.getCodeSource();
    URL location = codeSource.getLocation();
    URI uri = null;
	try {
		uri = location.toURI();
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
    Path path = Paths.get(uri);

    return path.toString().endsWith(".exe");
  }

}
