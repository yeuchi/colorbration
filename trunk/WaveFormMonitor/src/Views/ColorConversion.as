package Views
{
	public class ColorConversion
	{
		public var r:Number;
		public var g:Number;
		public var b:Number;
		
		public function ColorConversion()
		{
		}
		
		public function split(color:uint):void {
			r = Number((color&0xFF0000)>>16)/255.0;
			g = Number((color&0xFF00)>>8)/255.0;
			b = Number(color&0xFF)/255.0;
		}
	}
}