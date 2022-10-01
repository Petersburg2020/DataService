package nx.peter.java.storage;

import nx.peter.java.util.Util;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

/**
 * An abstract representation of file and directory pathnames.
 *
 * <p> User interfaces and operating systems use system-dependent <em>pathname
 * strings</em> to name files and directories.  This class presents an
 * abstract, system-independent view of hierarchical pathnames.  An
 * <em>abstract pathname</em> has two components:
 *
 * <ol>
 * <li> An optional system-dependent <em>prefix</em> string,
 *      such as a disk-drive specifier, {@code "/"}&nbsp;for the UNIX root
 *      directory, or {@code "\\\\"}&nbsp;for a Microsoft Windows UNC pathname, and
 * <li> A sequence of zero or more string <em>names</em>.
 * </ol>
 *
 * The first name in an abstract pathname may be a directory name or, in the
 * case of Microsoft Windows UNC pathnames, a hostname.  Each subsequent name
 * in an abstract pathname denotes a directory; the last name may denote
 * either a directory or a file.  The <em>empty</em> abstract pathname has no
 * prefix and an empty name sequence.
 *
 * <p> The conversion of a pathname string to or from an abstract pathname is
 * inherently system-dependent.  When an abstract pathname is converted into a
 * pathname string, each name is separated from the next by a single copy of
 * the default <em>separator character</em>.  The default name-separator
 * character is defined by the system property {@code file.separator}, and
 * is made available in the public static fields {@link
 * #separator} and {@link #separatorChar} of this class.
 * When a pathname string is converted into an abstract pathname, the names
 * within it may be separated by the default name-separator character or by any
 * other name-separator character that is supported by the underlying system.
 *
 * <p> A pathname, whether abstract or in string form, may be either
 * <em>absolute</em> or <em>relative</em>.  An absolute pathname is complete in
 * that no other information is required in order to locate the file that it
 * denotes.  A relative pathname, in contrast, must be interpreted in terms of
 * information taken from some other pathname.  By default the classes in the
 * {@code java.io} package always resolve relative pathnames against the
 * current user directory.  This directory is named by the system property
 * {@code user.dir}, and is typically the directory in which the Java
 * virtual machine was invoked.
 *
 * <p> The <em>parent</em> of an abstract pathname may be obtained by invoking
 * the {@link #getParent} method of this class and consists of the pathname's
 * prefix and each name in the pathname's name sequence except for the last.
 * Each directory's absolute pathname is an ancestor of any {@code File}
 * object with an absolute abstract pathname which begins with the directory's
 * absolute pathname.  For example, the directory denoted by the abstract
 * pathname {@code "/usr"} is an ancestor of the directory denoted by the
 * pathname {@code "/usr/local/bin"}.
 *
 * <p> The prefix concept is used to handle root directories on UNIX platforms,
 * and drive specifiers, root directories and UNC pathnames on Microsoft Windows platforms,
 * as follows:
 *
 * <ul>
 *
 * <li> For UNIX platforms, the prefix of an absolute pathname is always
 * {@code "/"}.  Relative pathnames have no prefix.  The abstract pathname
 * denoting the root directory has the prefix {@code "/"} and an empty
 * name sequence.
 *
 * <li> For Microsoft Windows platforms, the prefix of a pathname that contains a drive
 * specifier consists of the drive letter followed by {@code ":"} and
 * possibly followed by {@code "\\"} if the pathname is absolute.  The
 * prefix of a UNC pathname is {@code "\\\\"}; the hostname and the share
 * name are the first two names in the name sequence.  A relative pathname that
 * does not specify a drive has no prefix.
 *
 * </ul>
 *
 * <p> Instances of this class may or may not denote an actual file-system
 * object such as a file or a directory.  If it does denote such an object
 * then that object resides in a <i>partition</i>.  A partition is an
 * operating system-specific portion of storage for a file system.  A single
 * storage device (e.g. a physical disk-drive, flash memory, CD-ROM) may
 * contain multiple partitions.  The object, if any, will reside on the
 * partition <a id="partName">named</a> by some ancestor of the absolute
 * form of this pathname.
 *
 * <p> A file system may implement restrictions to certain operations on the
 * actual file-system object, such as reading, writing, and executing.  These
 * restrictions are collectively known as <i>access permissions</i>.  The file
 * system may have multiple sets of access permissions on a single object.
 * For example, one set may apply to the object's <i>owner</i>, and another
 * may apply to all other users.  The access permissions on an object may
 * cause some methods in this class to fail.
 *
 * <p> Instances of the {@code File} class are immutable; that is, once
 * created, the abstract pathname represented by a {@code File} object
 * will never change.
 *
 * <h2>Interoperability with {@code java.nio.file} package</h2>
 *
 * <p> The <a href="../../java/nio/file/package-summary.html">{@code java.nio.file}</a>
 * package defines interfaces and classes for the Java virtual machine to access
 * files, file attributes, and file systems. This API may be used to overcome
 * many of the limitations of the {@code java.io.File} class.
 * The {@link #toPath toPath} method may be used to obtain a {@link
 * Path} that uses the abstract path represented by a {@code File} object to
 * locate a file. The resulting {@code Path} may be used with the {@link
 * java.nio.file.Files} class to provide more efficient and extensive access to
 * additional file operations, file attributes, and I/O exceptions to help
 * diagnose errors when an operation on a file fails.
 *
 * @since   1.0
 */
public class File extends java.io.File {
    public static final String FILES_FOLDER = "src/nx/peter/store/";

    /**
     * Creates a new {@code File} instance by converting the given
     * pathname string into an abstract pathname.  If the given string is
     * the empty string, then the result is the empty abstract pathname.
     *
     * @param   pathname  A pathname string
     * @throws  NullPointerException
     *          If the {@code pathname} argument is {@code null}
     */
    public File(CharSequence pathname) {
        super(pathname != null ? pathname.toString() : "");
    }

    /**
     * Creates a new {@code File} instance from a parent pathname string
     * and a child pathname string.
     *
     * <p> If {@code parent} is {@code null} then the new
     * {@code File} instance is created as if by invoking the
     * single-argument {@code File} constructor on the given
     * {@code child} pathname string.
     *
     * <p> Otherwise the {@code parent} pathname string is taken to denote
     * a directory, and the {@code child} pathname string is taken to
     * denote either a directory or a file.  If the {@code child} pathname
     * string is absolute then it is converted into a relative pathname in a
     * system-dependent way.  If {@code parent} is the empty string then
     * the new {@code File} instance is created by converting
     * {@code child} into an abstract pathname and resolving the result
     * against a system-dependent default directory.  Otherwise each pathname
     * string is converted into an abstract pathname and the child abstract
     * pathname is resolved against the parent.
     *
     * @param   parent  The parent pathname string
     * @param   child   The child pathname string
     * @throws  NullPointerException
     *          If {@code child} is {@code null}
     */
    public File(CharSequence parent, CharSequence child) {
        super(parent != null ? parent.toString() : "", child != null ? child.toString() : "");
    }

    /**
     * Creates a new {@code File} instance from a parent abstract
     * pathname and a child pathname string.
     *
     * <p> If {@code parent} is {@code null} then the new
     * {@code File} instance is created as if by invoking the
     * single-argument {@code File} constructor on the given
     * {@code child} pathname string.
     *
     * <p> Otherwise the {@code parent} abstract pathname is taken to
     * denote a directory, and the {@code child} pathname string is taken
     * to denote either a directory or a file.  If the {@code child}
     * pathname string is absolute then it is converted into a relative
     * pathname in a system-dependent way.  If {@code parent} is the empty
     * abstract pathname then the new {@code File} instance is created by
     * converting {@code child} into an abstract pathname and resolving
     * the result against a system-dependent default directory.  Otherwise each
     * pathname string is converted into an abstract pathname and the child
     * abstract pathname is resolved against the parent.
     *
     * @param   parent  The parent abstract pathname
     * @param   child   The child pathname string
     * @throws  NullPointerException
     *          If {@code child} is {@code null}
     */
    public File(java.io.File parent, CharSequence child) {
        super(parent, child != null ? child.toString() : "");
    }

    /**
     * Creates a new {@code File} instance by converting the given
     * {@code file:} URI into an abstract pathname.
     *
     * <p> The exact form of a {@code file:} URI is system-dependent, hence
     * the transformation performed by this constructor is also
     * system-dependent.
     *
     * <p> For a given abstract pathname <i>f</i> it is guaranteed that
     *
     * <blockquote><code>
     * new File(</code><i>&nbsp;f</i><code>.{@link #toURI()
     * toURI}()).equals(</code><i>&nbsp;f</i><code>.{@link #getAbsoluteFile() getAbsoluteFile}())
     * </code></blockquote>
     *
     * so long as the original abstract pathname, the URI, and the new abstract
     * pathname are all created in (possibly different invocations of) the same
     * Java virtual machine.  This relationship typically does not hold,
     * however, when a {@code file:} URI that is created in a virtual machine
     * on one operating system is converted into an abstract pathname in a
     * virtual machine on a different operating system.
     *
     * @param  uri
     *         An absolute, hierarchical URI with a scheme equal to
     *         {@code "file"}, a non-empty path component, and undefined
     *         authority, query, and fragment components
     *
     * @throws  NullPointerException
     *          If {@code uri} is {@code null}
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameter do not hold
     *
     * @see #toURI()
     * @see URI
     * @since 1.4
     */
    public File(URI uri) {
        super(uri);
    }

    /**
     * Gets the extension of the file if it has one
     * @return extension of the file if it has one
     */
    public String getExtension() {
        return getName().contains(".") ? getName().substring(getName().lastIndexOf(".") + 1) : "";
    }


    /**
     * Gets the length of extension of the file if it has one
     * @return length of extension of the file if it has one
     */
    public int getExtensionLength() {
        return getExtension().length();
    }

    /**
     * Gets the name of the file without extension if it has one
     * @return name of the file without extension if it has one
     */
    public String getNameWithoutExtension() {
        return hasExtension() ? getName().substring(0, getName().lastIndexOf(".")) : getName();
    }

    /**
     * Checks if the file has an extension
     * @return true if the file has an extension, else false
     */
    public boolean hasExtension() {
        return !getExtension().isEmpty();
    }

    /**
     * Read from file
     * @return texts of lines in file
     */
    public List<String> readLines() {
        return FileManager.readLines(getPath());
    }

    /**
     * Appends or Write new texts to file
     * @param texts texts to write
     * @param append whether to append or not
     * @return true if it was written
     */
    public boolean write(CharSequence texts, boolean append) {
        return FileManager.writeFile(getPath(), texts, append);
    }

    /**
     * Appends or Write new texts to file
     * @param texts texts to write
     * @param append whether to append or not
     * @return true if it was written
     */
    public boolean write(List<String> texts, boolean append) {
        return FileManager.writeFile(getPath(), texts, append);
    }

    public String readString() {
        return Util.toString(readLines());
    }
}
