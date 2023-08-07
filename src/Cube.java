public class Cube {

    /** int that epresents the number of cubies in one side of a face of the cube.
     * Ex: A cube with SIZE = 3 would be a 3x3 cube, where each face is a 3x3 matrix.*/
    private final int SIZE = 3;

    /** The face that is on the top of the cube. Must have a size equivalent to SIZE. */
    private Face top;

    /** The face that is on the bottom of the cube. Will have a size equivalent to SIZE. */
    private Face bottom;

    /** The face that is on left side of the cube. Will have a size equivalent to SIZE. */
    private Face left;

    /** The face that is on right side of the cube. Will have a size equivalent to SIZE. */
    private Face right;

    /** The face that is on the front of the cube. Will have a size equivalent to SIZE. */
    private Face front;

    /** The face that is on the back of the cube. Will have a size equivalent to SIZE. */
    private Face back;

    Cube() {
        top = new Face(Face.Colors.RED, SIZE);
        bottom = new Face(Face.Colors.ORANGE, SIZE);
        left = new Face(Face.Colors.BLUE, SIZE);
        right = new Face(Face.Colors.GREEN, SIZE);
        front = new Face(Face.Colors.WHITE, SIZE);
        back = new Face(Face.Colors.YELLOW, SIZE);
    }

    /** Prints the cube to the console with three rows of faces, where the first row
     * contains only the top Face, which is positioned above the front Face. To the
     * left of the front Face in the second row is the left face, and to the right of
     * the front face is the right Face, followed by the back Face to its right.
     * In the bottom row, underneath the front Face is the bottom Face.
     *
     * Ex: the default, solved cube (RED on top, ORANGE on bottom, BLUE on left, GREEN on
     *      right, WHITE in front, and YELLOW in back) would be printed as:
     *
     *                  -------------
     *                  | R | R | R |
     *                  | R | R | R |
     *                  | R | R | R |
     *                  -------------
     * -------------    -------------    -------------    -------------
     * | B | B | B |    | W | W | W |    | G | G | G |    | Y | Y | Y |
     * | B | B | B |    | W | W | W |    | G | G | G |    | Y | Y | Y |
     * | B | B | B |    | W | W | W |    | G | G | G |    | Y | Y | Y |
     * -------------    -------------    -------------    -------------
     *                  -------------
     *                  | O | O | O |
     *                  | O | O | O |
     *                  | O | O | O |
     *                  -------------
     * */
    public void printCube () {

        String betweenFaces = "    ";
        String blankRow = " ";
        String faceEdge = "-";
        for (int i = 0; i < SIZE * 4; i++){
            blankRow += " ";
            faceEdge += "-";
        }

        // print top row of faces
        System.out.println(blankRow + betweenFaces + faceEdge);
        for (int i = 0; i < SIZE; i++) {
            System.out.println(blankRow + betweenFaces + top.rowToString(i));
        }
        System.out.println(blankRow + betweenFaces + faceEdge);

        // print middle row of faces
        for (int i = 0; i < 4; i++) {
            System.out.print(faceEdge + betweenFaces);
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.println(left.rowToString(i) + betweenFaces + front.rowToString(i)
                    + betweenFaces + right.rowToString(i) + betweenFaces + back.rowToString(i));
        }

        for (int i = 0; i < 4; i++) {
            System.out.print(faceEdge + betweenFaces);
        }
        System.out.println();

        // print bottom row of faces
        System.out.println(blankRow + betweenFaces + faceEdge);
        for (int i = 0; i < SIZE; i++) {
            System.out.println(blankRow + betweenFaces + bottom.rowToString(i));
        }
        System.out.println(blankRow + betweenFaces + faceEdge);

    }

}
