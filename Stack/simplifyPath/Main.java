import java.util.Stack;

public class Main {

    /**
     * Simplifies a Unix-style absolute path.
     *
     * @param path The input path string.
     * @return The simplified canonical path.
     */
    public static String simplifyPath(String path) {
        // Use a Stack to keep track of valid directory names.
        Stack<String> stack = new Stack<>();

        // Split the path by '/' delimiter. This will produce an array of components.
        // Note: path.split("/") can result in empty strings for multiple slashes
        // or leading/trailing slashes, which we will handle.
        String[] components = path.split("/");

        // Iterate through each component obtained from splitting the path.
        for (String component : components) {
            // Case 1: Empty string or current directory ('.').
            // These components do not affect the simplified path, so we skip them.
            if (component.equals("") || component.equals(".")) {
                continue;
            }
            // Case 2: Parent directory ('..').
            // If the stack is not empty, it means we can go up one level.
            // So, pop the last directory from the stack.
            else if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
                // If the stack is empty, it means we are at the root,
                // and '..' has no effect (cannot go above root). Do nothing.
            }
            // Case 3: A valid directory name.
            // Push the directory name onto the stack.
            else {
                stack.push(component);
            }
        }

        // After processing all components, construct the simplified path from the stack.
        // If the stack is empty, it means the path simplifies to the root directory "/".
        if (stack.isEmpty()) {
            return "/";
        }

        // Use a StringBuilder for efficient string concatenation.
        StringBuilder result = new StringBuilder();
        // The canonical path always starts with a '/'.
        // Iterate through the elements in the stack. The order of iteration
        // for Stack (which implements Iterable) is from bottom to top,
        // which is exactly what we need for building the path.
        for (String dir : stack) {
            result.append("/");
            result.append(dir);
        }

        // Convert the StringBuilder content to a String and return.
        return result.toString();
    }

    // Main method for testing the simplifyPath function.
    public static void main(String[] args) {
        // Test cases
        System.out.println("'/home/' -> " + simplifyPath("/home/")); // Expected: "/home"
        System.out.println("'/../' -> " + simplifyPath("/../")); // Expected: "/"
        System.out.println("'/home//foo/' -> " + simplifyPath("/home//foo/")); // Expected: "/home/foo"
        System.out.println("'/a/./b/../../c/' -> " + simplifyPath("/a/./b/../../c/")); // Expected: "/c"
        System.out.println("'/a/../../b/../c//.//' -> " + simplifyPath("/a/../../b/../c//.//")); // Expected: "/c"
        System.out.println("'/a/b/c' -> " + simplifyPath("/a/b/c")); // Expected: "/a/b/c"
        System.out.println("'/.' -> " + simplifyPath("/.")); // Expected: "/"
        System.out.println("'/' -> " + simplifyPath("/")); // Expected: "/"
        System.out.println("'/.../a/../b/c/../d/.' -> " + simplifyPath("/.../a/../b/c/../d/.")); // Expected: "/.../b/d"
    }
}