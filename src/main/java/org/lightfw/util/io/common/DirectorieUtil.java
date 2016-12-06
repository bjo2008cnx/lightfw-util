
package org.lightfw.util.io.common;

import com.google.common.collect.Iterables;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录操作工具类
 */
public final class DirectorieUtil {
    /**
     * 计算目录下的条目个数
     *
     * @param dir directory to evaluate
     * @return number of inodes under it.
     * @throws java.io.IOException
     */
    @Nonnegative
    public static int count(@Nonnull final Path dir) throws IOException {
        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            return Iterables.size(stream);
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
            throw ex.getCause();
        }
    }

    /**
     * 列表文件夹下子文件夹（不要用在超大文件夹）
     *
     * @param dir directory to evaluate
     * @return all files in that directory
     * @throws java.io.IOException
     */
    @Nonnull
    public static List<Path> list(@Nonnull final Path dir) throws IOException {
        final List<Path> contents = new ArrayList<>();
        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Path entry : stream) {
                contents.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause();
        }
        return contents;
    }

    private DirectorieUtil() { /* no */ }
}
