public class QuadTree {
    private final int MAX_SIZE = 4;
    private QuadTree northWest, northEast, southWest, southEast;
    private AABB boundary;
    private Point[] points = new Point[MAX_SIZE];

    public QuadTree(AABB boundary) {
        this.boundary = boundary;
    }

    public boolean insert(Point point) {
        if(!boundary.contains(point)) return false;

        if(northWest == null) {
            for(int i = 0; i < points.length; i++) {
                if(points[i] == null) {
                    points[i] = point;
                    return true;
                }
            }

            subdivide();
            if(northWest.insert(point)) return true;
            if(northEast.insert(point)) return true;
            if(southWest.insert(point)) return true;
            if(southEast.insert(point)) return true;
        }
        return false;
    }

    public void subdivide() {
        northWest = new QuadTree(new AABB(
                new Point(boundary.center.x-boundary.halfDimension/2, boundary.center.y-boundary.halfDimension/2),
                boundary.halfDimension/2));
        northEast = new QuadTree(new AABB(
                new Point(boundary.center.x+boundary.halfDimension/2, boundary.center.y-boundary.halfDimension/2),
                boundary.halfDimension/2));
        southWest = new QuadTree(new AABB(
                new Point(boundary.center.x-boundary.halfDimension/2, boundary.center.y+boundary.halfDimension/2),
                boundary.halfDimension/2));
        southEast = new QuadTree(new AABB(
                new Point(boundary.center.x+boundary.halfDimension/2, boundary.center.y+boundary.halfDimension/2),
                boundary.halfDimension/2));
    }

    public static void main(String[] args) {
        QuadTree tree = new QuadTree(new AABB(new Point(100, 100), 50));
        Point p1 = new Point(10, 10);
        Point p2 = new Point(15, 10);
        Point p3 = new Point(10, 17);
        Point p4 = new Point(74, 79);
        Point p5 = new Point(99, 99);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
    }

    static class AABB {
        Point center;
        float halfDimension;

        public AABB(Point center, float halfDimension) {
            this.center = center;
            this.halfDimension = halfDimension;
        }

        public boolean contains(Point point) {
            if(point.x >= center.x-halfDimension
               && point.x <= center.x+halfDimension
               && point.y >= center.y-halfDimension
               && point.y <= center.y+halfDimension) {
                return true;
            }
            return false;
        }
    }

    static class Point {
        private float x,y;

        public Point() { }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
