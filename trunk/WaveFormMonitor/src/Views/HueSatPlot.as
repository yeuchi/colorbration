package Views
{
	import flash.display.BitmapData;
	
	import mx.core.UIComponent;
	
	public class HueSatPlot extends Plot
	{
		public function HueSatPlot()
		{
			super();
		}
		
		override public function set source(bmd:BitmapData):void {
			bmdSrc = bmd;
			this.graphics.clear();
			this.drawGrids();
			invalidate();
		}
		
		//public function invalidate():void {
//			for(var y:int=0; y<bmdSrc.height; y++) {
//				for(var x:int=0; x<bmdSrc.width; x++) {
//			}
		//}
	}
}