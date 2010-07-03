package Views
{
	import flash.display.BitmapData;
	import flash.geom.Point;
	
	import mx.core.UIComponent;
	
	public class YIQPlot extends Plot
	{
		public static const SPACE:String = "SPACE_YIQ";
		protected var convert:RGB2YIQ;
		
		public function YIQPlot()
		{
			super();
			convert = new RGB2YIQ();
		}
		
		override protected function renderColor():void {
			var stepX:Number = Number(bmdSrc.width)/Number(this.width);
			var amp:Number;
			
			for(var y:int=0; y<bmdSrc.height; y+=pixelRate) {
				for(var x:int=1; x<bmdSrc.width; x+=pixelRate) {
					// 1st point
					var clr:uint = bmdSrc.getPixel(x,y);
					convert.forwardChroma(clr);
					this.graphics.lineStyle(1, clr);
					var X:Number = convert.i*ptMid.x+ptMid.x;
					var Y:Number = convert.q*ptMid.y+ptMid.y;
					this.graphics.drawCircle(X, 
						Y,1);
					this.graphics.endFill();
				}
			}
			graphics.endFill();
		}
		
		override protected function renderValue():void {
			this.graphics.lineStyle(1, lineClr);
			var stepX:Number = Number(bmdSrc.width)/Number(this.width);
			var amp:Number;
			
			for(var y:int=0; y<bmdSrc.height; y+=pixelRate) {
				this.graphics.moveTo(0, this.height-dcValue);
				for(var x:int=0; x<this.width; x+=pixelRate) {
					var clr:uint = bmdSrc.getPixel(int(x*stepX),y);
					convert.forwardValue(clr);
					amp = convert.y*ampValue+dcValue;
					if(amp>this.height)
						amp = this.height;
					else if(amp < 0)
						amp = 0;
					this.graphics.lineTo(x, amp);
				}
			}
			graphics.endFill();
		}
	}
}