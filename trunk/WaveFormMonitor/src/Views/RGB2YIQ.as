package Views
{
	public class RGB2YIQ extends ColorConversion
	{
		public var y:Number;
		public var i:Number;
		public var q:Number;
		
		public function RGB2YIQ()
		{
		}
		
		public function forwardChroma (color:uint):void {
			split(color);
			i = .60 * r - .28 * g - .32 * b;
			q = .21 * r - .52 * g * .31 * b;
		}
		
		public function forwardValue (color:uint):void {
			split(color);
			y = .3 * r + .59 * g + .11 * b;
		}
	}
}