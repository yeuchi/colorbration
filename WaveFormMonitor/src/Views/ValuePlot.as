package Views
{
	import flash.display.BitmapData;
	
	import mx.core.UIComponent;
	
	public class ValuePlot extends Plot
	{
		public static const VALUE_COLOR:uint = 0x88888888;
		
		public function ValuePlot()
		{
			super();
			lineClr = VALUE_COLOR;
		}
		
		override public function set source(bmd:BitmapData):void {
			bmdSrc = bmd;
			this.graphics.clear();
			this.drawGrids();
			invalidate();
		}
		
//		override public function invalidate():void {
//			this.graphics.lineStyle(1, lineClr);
//			var stepX:Number = Number(bmdSrc.width)/Number(this.width);
//			
//			for(var y:int=0; y<bmdSrc.height; y++) {
//				this.graphics.moveTo(0, this.height-dc);
//				for(var x:int=0; x<this.width; x++) {
//					var clr:uint = bmdSrc.getPixel(int(x*stepX),y);
//					var v:Number = Number(clr&0xFF)*.114 +
//						           (Number(clr&0xFF00)>>8)*.587 +
//								   (Number(clr&0xFF0000)>>16)*.299;
//					this.graphics.lineTo(x, v*multiplier);
//				}
//			}
//			graphics.endFill();
//		}
	}
}