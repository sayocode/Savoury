package main.java.conversion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import main.java.conversion.dto.ConvertResultDto;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * ファイル名変更クラス.
 * 
 * @author sayoko
 */
public class ChangeFileName {

  /**
   * ファイル名置換.
   * 
   * @param pathInputText
   *      パス名
   * @param beforeText
   *      置換前テキスト
   * @param afterText
   *      置換後テキスト
   * @return 処理結果
   * @throws IOException
   *       IOException
   */
  public ConvertResultDto conversionFileName(String pathInputText, String beforeText,
          String afterText, String extensionInputText) throws IOException {

    ConvertResultDto convertResultDto = new ConvertResultDto();
    convertResultDto.setResult(false);

    // パスチェック
    if (StringUtils.isEmpty(pathInputText) || StringUtils.isEmpty(beforeText)) {
      convertResultDto.setMessage(Constants.Message.ERROR_REQUIRED);
      return convertResultDto;
    }
    Path path = Paths.get(pathInputText).toRealPath(LinkOption.NOFOLLOW_LINKS);
    if (!Files.isDirectory(path)) {
      convertResultDto.setMessage(Constants.Message.ERROR_NO_DIRECTORY);
      return convertResultDto;
    }

    // 対象フォルダの指定
    File file = new File(pathInputText);
    File[] files = file.listFiles();
    int length = (files.length);

    if (length == 0 || files == null) {
      convertResultDto.setMessage(Constants.Message.ERROR_FILE_NOT_FOUND);
      return convertResultDto;
    }

    String folderName = file.getPath().toString();
    folderName = folderName.replaceAll("\\\\", "/");

    // 拡張子指定の有無確認
    boolean isExtension = !StringUtils.isEmpty(extensionInputText);
    String[] extensionList = null;
    if (isExtension) {
      extensionInputText = extensionInputText.replaceAll("\\.", "").replaceAll(" ", "");
      extensionList = extensionInputText.split(",");
    }

    // ファイル名を一個ずつ検証し名前を変更する。
    int numberOfFiles = 0;
    for (File f : files) {
      String name = f.getName();
      String oldFileName = FilenameUtils.getBaseName(name.toString());

      // ファイルの拡張子
      String extension = "." + FilenameUtils.getExtension(name);

      // 拡張子指定がない場合で指定された拡張子のファイルでない場合、処理を行わない。
      if (isExtension && !Arrays.asList(extensionList).contains(extension.replace(".", ""))) {
        continue;
      }

      // 置換前の単語を含むファイルでない場合は処理を行わない。
      if (oldFileName.indexOf(beforeText) == -1) {
        continue;
      }

      numberOfFiles++;

      String fileName = "/" + oldFileName.replace(beforeText, afterText);

      f.renameTo(new File(folderName + fileName + extension));
    }

    // 一件も名前の変更をしなかった場合はエラーとする
    if (numberOfFiles < 1) {
      convertResultDto.setMessage(Constants.Message.ERROR_FILES_THAT_MEET_THE_CRITERIA);
      return convertResultDto;
    }

    // 処理成功
    convertResultDto.setResult(true);
    convertResultDto.setNumberOfFiles(numberOfFiles);
    convertResultDto.setMessage(Constants.Message.RESULT_OK);
    return convertResultDto;
  }
}