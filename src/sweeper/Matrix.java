package sweeper;

public class Matrix {

    private Box[][] matrix;

    Matrix(Box box){
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoord()) {
            matrix[coord.x][coord.y] = box;
        }
    }

    Box get(Coord coord) {
        if(Ranges.inRange(coord)) {
            return matrix[coord.x][coord.y];
        }
        return null;
    }


    void set (Coord coord, Box box) {
        matrix[coord.x][coord.y] = box;
    }





}
