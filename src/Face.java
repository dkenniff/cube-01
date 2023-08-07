import java.util.Arrays;

public class Face {

    /** The six possible colors any piece on a face can be. */
    public static enum Colors { WHITE, YELLOW, GREEN, BLUE, RED, ORANGE }


    /** Returns the String representation of cl, "W" for WHITE, "Y" for YELLOW, etc.
     * cl must be a valid entry in Colors. */
    private String colorToString(Colors cl) {
        switch (cl) {
            case WHITE:
                return "W";
            case YELLOW:
                return "Y";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            case RED:
                return "R";
            case ORANGE:
                return "O";
        }
        return null;
    }

    /** The matrix that stores the color of each piece in this face. Colors[r][c] accesses
     * the piece in the rth row and cth column (0-indexed) of this face. */
    Colors[][] face;

    /** Number of pieces across or down in this face. This face has size rows and columns,
     * creating a total of (size * size) pieces. */
    private int size;

    Face(Colors cl, int sz) {
        size = sz;
        face = new Colors[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                face[i][j] = cl;
            }

        }
    }

    Face(Colors[][] m) {
        size = m.length;
        face = new Colors[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                face[i][j] = m[i][j];
            }
        }
    }

    /** Returns the size of this face, which is the number of rows or columns it has. */
    public int size() { return size; }

    /** Returns the matrix that represents this face. */
    public Colors[][] matrix() { return face; }


    /** Returns the piece in the (row, column) 0-indexed position of this face. row and column must
     * both be less than or equal to the size of this face.
     *
     * Ex: cubie(1, 2) on this face ----> | B | B | G |
     *     returns Colors.YELLOW          | R | O |(Y)|
     *     (see parentheses)              | R | W | B |
     *     */
    public Colors cubie(int row, int column) throws IndexOutOfBoundsException {
        if (row >= size || column >= size) throw new IndexOutOfBoundsException();
        return face[row][column]; }

    /** Replaces the current color in [row, column] with color cl. */
    public void replaceCubie(int row, int column, Colors cl) {
        face[row][column] = cl;
    }


    /** Returns the r-th row of this face. r must be less than or equal to the size of this face. */
    public Colors[] row(int r) {
        Colors[] row = new Colors[size];
        for (int i = 0; i < size; i++) {
            row[i] = cubie(r, i);
        }
        return row;
    }

    /** Returns the c-th column of this face. c must be less than or equal to the size of this face. */
    public Colors[] col(int c) {
        Colors[] col = new Colors[size];
        for (int i = 0; i < size; i++) {
            col[i] = cubie(i, c);
        }
        return col;
    }

    /** Converts the r-th row of this face into a string, where the color of each piece is
     * represented by its first letter (i.e. "W" for white, etc.), separated by " | ".
     *
     * Ex: A row of a random 3x3 face ---->  | R | B | R |     */
    public String rowToString(int r) {

        String row = "|";

        for (int i = 0; i < size; i++) {
            row = row + " " + colorToString(face[r][i]) + " |";
        }

        return row;
    }

    /** Prints this face as output, with each piece separated by " | " and a row of "-"
     * above and below the face. The colors are represented by the first letter of their color,
     * i.e. "W" for white, "B" for blue, etc.
     *
     * Ex: a 3x3 face of all white pieces ----> --------------
     *                                          | W | W | W |
     *                                          | W | W | W |
     *                                          | W | W | W |
     *                                          --------------
     *                                          */
    public void printFace() {

        String divider = "";
        for (int i = 0; i < 4.5 * size; i++) {
            divider += "-";
        }

        System.out.println(divider);

        for (int j = 0; j < size; j++) {
            System.out.println(rowToString(j));
        }

        System.out.println(divider);

    }

    /** Replaces the rowNum-th row in this face with newRow and returns the old row. */
    public Colors[] swapRow(Colors[] newRow, int rowNum) {
       Colors[] oldRow = row(rowNum);
       face[rowNum] = newRow;
       return oldRow;
    }

    /** Replaces the colNum-th column in this face with newCol and returns the old column. */
    public Colors[] swapCol(Colors[] newCol, int colNum) {
        Colors[] oldCol = col(colNum);
        for (int i = 0; i < size; i++) {
            face[i][colNum] = newCol[i];
        }
        return oldCol;
    }

    /** Returns true if both arrays have the same colors in the same positions and false
     * otherwise. */
    private boolean compArrays(Colors[] a1, Colors[] a2) throws IndexOutOfBoundsException{
        if (a1.length != size || a2.length != size) throw new IndexOutOfBoundsException();
        for (int i = 0; i < size; i++) { if (a1[i] != a2[i]) return false; }
        return true;
    }

    /** Returns true if all the entries in the r-th row of this face are equivalent to their
     * corresponding entries in newRow, and false otherwise.  */
    public boolean rowEquals(int r, Colors[] newRow) {
        //Colors[] oldRow = row(r);

        //for (int i = 0; i < size; i++) {
           // if (oldRow[i] != newRow[i]) return false;
        //}

        //return true;
        return compArrays(row(r), newRow);
    }

    /** Returns true if all the entries in the c-th column of this face are equivalent to their
     * corresponding entries in newCol, and false otherwise. */
    public boolean colEquals(int c, Colors[] newCol) {
        return compArrays(col(c), newCol);
    }

    /** Returns true if every row in this face is equal to every row in matrix f and false otherwise. */
    public boolean matrixEquals(Colors[][] f) {
        if (f.length != size()) return false;
        for (int i = 0; i < size(); i++) {
            if (!rowEquals(i, f[i])) return false;
        }
        return true;
    }

    /** Rotates the face clockwise by moving cubies around the face 90 degrees clockwise. */
    public void rotateFaceCW() {
        Colors[][] newFace = new Colors[size()][size()];

        for (int newRow = 0; newRow < size(); newRow++) {
            Colors[] oldCol = col(newRow);

            for (int rowIndex = 0; rowIndex < size(); rowIndex++) {
                newFace[newRow][rowIndex] = oldCol[oldCol.length - 1 - rowIndex];
            }
        }

        face = newFace;

    }

    /** Rotates the face clockwise by moving cubies around the face 90 degrees
     * counter-clockwise. */
    public void rotateFaceCCW() {
        rotateFaceCW();
        rotateFaceCW();
        rotateFaceCW();
    }

}
