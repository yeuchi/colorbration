package GradientMethods
{
	import flash.geom.Point;

	public class Line
	{
		protected var m:Number;
		protected var b:Number;
		
		public function Line(p0:Point, p1:Point)
		{
			m = (p1.y-p0.y)/(p1.x-p0.x);
			b = p1.y-m*p1.x;
		}
		
		public function distance(p:Point):Number {
			var dis:Number = Math.abs(m*p.x-p.y+b) / Math.sqrt(m*m+1);
			return dis;
		}
	}
}