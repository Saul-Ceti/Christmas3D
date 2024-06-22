public class Shapes {
    public Shapes(){

    }

    //-------------------------- PIRAMIDE TRIANGULAR --------------------------//
    public int[][] verticesTriangularPrism(int a, int h){
        double sqrt3 = Math.sqrt(3);
        return new int[][] {
                {-a / 2, (int) (-a * sqrt3 / 6), -h / 2},
                {a / 2, (int) (-a * sqrt3 / 6), -h / 2},
                {0, (int) (a * sqrt3 / 3), -h / 2},
                {-a / 2, (int) (-a * sqrt3 / 6), h / 2},
                {a / 2, (int) (-a * sqrt3 / 6), h / 2},
                {0, (int) (a * sqrt3 / 3), h / 2}
        };
    }

    public int[][] sidesTriangularPrism(){
        return  new int[][]{
                {0, 1, 2},  // Base inferior
                {3, 4, 5},  // Base superior
                {0, 1, 4, 3}, // Cara lateral 1
                {1, 2, 5, 4}, // Cara lateral 2
                {2, 0, 3, 5}  // Cara lateral 3
        };
    }

    //--------------------------------- CUBO ----------------------------------//
    public int[][] verticesRectangularPrism(int a, int b, int h){
        return new int[][] {
                {-a / 2, -b / 2, -h / 2},  // Vértice 0
                {a / 2, -b / 2, -h / 2},   // Vértice 1
                {a / 2, b / 2, -h / 2},    // Vértice 2
                {-a / 2, b / 2, -h / 2},   // Vértice 3
                {-a / 2, -b / 2, h / 2},   // Vértice 4
                {a / 2, -b / 2, h / 2},    // Vértice 5
                {a / 2, b / 2, h / 2},     // Vértice 6
                {-a / 2, b / 2, h / 2}     // Vértice 7
        };
    }

    public int[][] sidesRectangularPrism(){
        return new int[][] {
                {0, 1, 2, 3},   // Base inferior
                {4, 5, 6, 7},   // Base superior
                {0, 1, 5, 4},   // Cara lateral 1
                {1, 2, 6, 5},   // Cara lateral 2
                {2, 3, 7, 6},   // Cara lateral 3
                {3, 0, 4, 7}    // Cara lateral 4
        };
    }

    //-------------------------- PIRAMIDE HEXAGONAL --------------------------//
    public int[][] verticesHexagonalPyramid(int a, int h) {
        double sqrt3 = Math.sqrt(3);

        return new int[][] {
                // Vértices de la base hexagonal en el plano XY
                {-a, 0, -h / 2},
                {-a / 2, (int) (a * sqrt3 / 2), -h / 2},
                {a / 2, (int) (a * sqrt3 / 2), -h / 2},
                {a, 0, -h / 2},
                {a / 2, -(int) (a * sqrt3 / 2), -h / 2},
                {-a / 2, -(int) (a * sqrt3 / 2), -h / 2},

                // Vértice de la punta superior de la pirámide
                {0, 0, h / 2}
        };
    }

    public int[][] sidesHexagonalPyramid() {
        return new int[][] {
                {0, 1, 2, 3, 4, 5},    // Base hexagonal
                {0, 1, 6},             // Triangular faces from vertex 0
                {1, 2, 6},             // Triangular faces from vertex 1
                {2, 3, 6},             // Triangular faces from vertex 2
                {3, 4, 6},             // Triangular faces from vertex 3
                {4, 5, 6},             // Triangular faces from vertex 4
                {5, 0, 6}              // Triangular faces from vertex 5
        };
    }

    //------------------------------- PRISMA HEXAGONAL --------------------------------//
    public int[][] verticesHexagonalPrism(int a, int h) {
        double sqrt3 = Math.sqrt(3);

        return new int[][]{
                // Vértices de la base hexagonal inferior en el plano XY
                {-a, 0, -h / 2},
                {-a / 2, (int) (a * sqrt3 / 2), -h / 2},
                {a / 2, (int) (a * sqrt3 / 2), -h / 2},
                {a, 0, -h / 2},
                {a / 2, -(int) (a * sqrt3 / 2), -h / 2},
                {-a / 2, -(int) (a * sqrt3 / 2), -h / 2},
                // Vértices de la base hexagonal superior en el plano XY
                {-a, 0, h / 2},
                {-a / 2, (int) (a * sqrt3 / 2), h / 2},
                {a / 2, (int) (a * sqrt3 / 2), h / 2},
                {a, 0, h / 2},
                {a / 2, -(int) (a * sqrt3 / 2), h / 2},
                {-a / 2, -(int) (a * sqrt3 / 2), h / 2}
        };
    }

    public int[][] sidesHexagonalPrism() {
        return new int[][] {
                // Caras hexagonales
                {0, 1, 2, 3, 4, 5},    // Base hexagonal inferior
                {6, 7, 8, 9, 10, 11},  // Base hexagonal superior
                // Caras rectangulares laterales
                {0, 1, 7, 6},          // Cara lateral desde el vértice 0 al 1
                {1, 2, 8, 7},          // Cara lateral desde el vértice 1 al 2
                {2, 3, 9, 8},          // Cara lateral desde el vértice 2 al 3
                {3, 4, 10, 9},         // Cara lateral desde el vértice 3 al 4
                {4, 5, 11, 10},        // Cara lateral desde el vértice 4 al 5
                {5, 0, 6, 11}          // Cara lateral desde el vértice 5 al 0
        };
    }
}
