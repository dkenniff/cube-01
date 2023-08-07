import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class FaceTest {

    @Test
    public void testInitFace() {
        for (Face.Colors cl : Face.Colors.values()) {
            Face f = new Face(cl, 3);
            assert (f.size() == 3);

            for (int i = 0; i < f.size(); i++) {
                for (int j = 0; j < f.size(); j++) {
                    assert (f.cubie(i, j) == cl);
                }
            }
            //f.printFace();
        }
    }

    @Test
    public void testSize() {
        Face f;
        // test all sizes from 0 to 100
        for (int i = 0; i < 100; i++) {
            f = new Face(Face.Colors.RED, i);
            assert (f.size() == i);
        }
    }

    @Test
    public void testCubie() {
        // test with size one
        Face f1 = new Face(Face.Colors.RED, 1);
        assert (f1.cubie(0,0) == Face.Colors.RED);

        // test with size >1
        Face f2 = new Face(Face.Colors.GREEN, 3);
        assert (f2.cubie(1,1) == Face.Colors.GREEN);
        assert (f2.cubie(2,1) == Face.Colors.GREEN);

        // test a cubie that doesn't exist
        assertThrows(IndexOutOfBoundsException.class, () -> f2.cubie(0,3));

        // test with size 0
        Face f3 = new Face(Face.Colors.BLUE, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> f3.cubie(0,0));
    }

    @Test
    public void testSwaps() {
        // check row comparison
        Face f = new Face(Face.Colors.WHITE, 3);
        Face.Colors[] expRow = {Face.Colors.WHITE, Face.Colors.WHITE, Face.Colors.WHITE};
        assert(f.rowEquals(0, expRow));

        // swap rows
        Face.Colors[] newRow = {Face.Colors.RED, Face.Colors.GREEN, Face.Colors.BLUE};
        Face.Colors[] swappedRow = f.swapRow(newRow, 1);

        // check row swap is successful
        assert(f.rowEquals(0, swappedRow));
        assert(f.rowEquals(1, newRow));

        // check specific cubies
        assert(f.cubie(0,1) == Face.Colors.WHITE);
        assert(f.cubie(2,2) == Face.Colors.WHITE);
        assert(f.cubie(1,0) == Face.Colors.RED);
        assert(f.cubie(1,1) == Face.Colors.GREEN);
        assert(f.cubie(1,2) == Face.Colors.BLUE);

        f.printFace();

        // change a second row
        Face.Colors[] newRow2 = {Face.Colors.ORANGE, Face.Colors.ORANGE, Face.Colors.WHITE};
        Face.Colors[] swappedRow2 = f.swapRow(newRow2, 2);


        // check row swap is successful
        assert(f.rowEquals(0, swappedRow2));
        assert(f.rowEquals(2, newRow2));

        f.printFace();

        // column swap
        Face.Colors[] swappedCol = f.swapCol(newRow2, 0);
        Face.Colors[][] newMatrix = {
                {Face.Colors.ORANGE, Face.Colors.WHITE, Face.Colors.WHITE},
                {Face.Colors.ORANGE, Face.Colors.GREEN, Face.Colors.BLUE},
                {Face.Colors.WHITE, Face.Colors.ORANGE, Face.Colors.WHITE},
        };

        f.printFace();

        // check column swap successful
        assertEquals(f.cubie(0,0), Face.Colors.ORANGE);
        assertEquals(f.cubie(1,0), Face.Colors.ORANGE);
        assertEquals(f.cubie(2,0), Face.Colors.WHITE);

        Face.Colors[] expNewCol = {Face.Colors.ORANGE, Face.Colors.ORANGE, Face.Colors.WHITE};
        assert(f.colEquals(0, expNewCol));
        assert(f.matrixEquals(newMatrix));

        // test two faces made from same matrix
        Face f1 = new Face(newMatrix);
        Face f2 = new Face(newMatrix);

        f1.swapCol(new Face.Colors[]{Face.Colors.ORANGE, Face.Colors.GREEN, Face.Colors.WHITE},0);
        assert(!f1.matrixEquals(f2.matrix()));

        f2.swapCol(new Face.Colors[]{Face.Colors.ORANGE, Face.Colors.GREEN, Face.Colors.WHITE},0);
        assert(f1.matrixEquals(f2.matrix()));


    }

    @Test
    public void testRotations() {
        Face.Colors[][] m1 =
                {
                        {Face.Colors.WHITE, Face.Colors.GREEN},
                        {Face.Colors.RED, Face.Colors.BLUE}
                };

        // test one clockwise rotation
        Face.Colors[][] m2 =
                {
                        {Face.Colors.RED, Face.Colors.WHITE},
                        {Face.Colors.BLUE, Face.Colors.GREEN}
                };

        Face f1 = new Face(m1);
        f1.rotateFaceCW();
        f1.printFace();
        assert(f1.matrixEquals(m2));


        // test one clockwise and one counterclockwise rotation
        f1.rotateFaceCCW();
        assert(f1.matrixEquals(m1));

        // test a 1x1 rotation
        Face f2 = new Face(new Face.Colors[][]{{Face.Colors.RED}});
        f2.rotateFaceCW();
        assert(f2.matrixEquals(new Face.Colors[][]{{Face.Colors.RED}}));
        f2.rotateFaceCCW();
        assert(f2.matrixEquals(new Face.Colors[][]{{Face.Colors.RED}}));

        // test a 0x0 rotation
        Face.Colors[][] empty = {};
        Face f3 = new Face(empty);
        f3.rotateFaceCW();
        assert(f3.matrixEquals(empty));
        f3.rotateFaceCCW();
        assert(f3.matrixEquals(empty));

        // test a 3x3 rotation
        Face.Colors[][] m3 =
                {
                        {Face.Colors.GREEN, Face.Colors.RED, Face.Colors.RED},
                        {Face.Colors.WHITE, Face.Colors.RED, Face.Colors.BLUE},
                        {Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.ORANGE},
                };

        Face.Colors[][] m3prime =
                {
                        {Face.Colors.YELLOW, Face.Colors.WHITE, Face.Colors.GREEN},
                        {Face.Colors.ORANGE, Face.Colors.RED, Face.Colors.RED},
                        {Face.Colors.ORANGE, Face.Colors.BLUE, Face.Colors.RED},
                };

        Face f4 = new Face(m3);
        f4.printFace();
        f4.rotateFaceCW();
        f4.printFace();
        assert(f4.matrixEquals(m3prime));
        f4.rotateFaceCCW();
        f4.printFace();
        assert(f4.matrixEquals(m3));


        // test a 7x7 rotation
        Face.Colors[][] m4 =
                {
                        {Face.Colors.RED, Face.Colors.WHITE, Face.Colors.WHITE, Face.Colors.YELLOW, Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.BLUE},
                        {Face.Colors.BLUE, Face.Colors.ORANGE, Face.Colors.GREEN, Face.Colors.RED, Face.Colors.WHITE, Face.Colors.YELLOW, Face.Colors.ORANGE},
                        {Face.Colors.GREEN, Face.Colors.RED, Face.Colors.RED, Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.ORANGE, Face.Colors.YELLOW},
                        {Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.WHITE, Face.Colors.RED, Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.GREEN},
                        {Face.Colors.GREEN, Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.ORANGE, Face.Colors.WHITE, Face.Colors.WHITE, Face.Colors.GREEN},
                        {Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.BLUE, Face.Colors.YELLOW, Face.Colors.YELLOW, Face.Colors.WHITE, Face.Colors.ORANGE},
                        {Face.Colors.BLUE, Face.Colors.ORANGE, Face.Colors.BLUE, Face.Colors.WHITE, Face.Colors.RED, Face.Colors.GREEN, Face.Colors.RED}
                };

        Face.Colors[][] m4prime =
                {
                        {Face.Colors.BLUE, Face.Colors.GREEN, Face.Colors.GREEN, Face.Colors.YELLOW, Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.RED},
                        {Face.Colors.ORANGE, Face.Colors.BLUE, Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.RED, Face.Colors.ORANGE, Face.Colors.WHITE},
                        {Face.Colors.BLUE, Face.Colors.BLUE, Face.Colors.ORANGE, Face.Colors.WHITE, Face.Colors.RED, Face.Colors.GREEN, Face.Colors.WHITE},
                        {Face.Colors.WHITE, Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.RED, Face.Colors.GREEN, Face.Colors.RED, Face.Colors.YELLOW},
                        {Face.Colors.RED, Face.Colors.YELLOW, Face.Colors.WHITE, Face.Colors.GREEN, Face.Colors.BLUE, Face.Colors.WHITE, Face.Colors.GREEN},
                        {Face.Colors.GREEN, Face.Colors.WHITE, Face.Colors.WHITE, Face.Colors.BLUE, Face.Colors.ORANGE, Face.Colors.YELLOW, Face.Colors.BLUE},
                        {Face.Colors.RED, Face.Colors.ORANGE, Face.Colors.GREEN, Face.Colors.GREEN, Face.Colors.YELLOW, Face.Colors.ORANGE, Face.Colors.BLUE}
                };

        Face f5 = new Face(m4);
        //f5.printFace();
        f5.rotateFaceCW();
        //f5.printFace();
        assert(f5.matrixEquals(m4prime));
        f5.rotateFaceCCW();
        assert(f5.matrixEquals(m4));
        //f5.printFace();

        // test a 8x8 rotation

    // CUBE TESTS
    Cube basicCube = new Cube();
    basicCube.printCube();

    }

}
