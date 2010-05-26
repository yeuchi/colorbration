package Views
{
	import flash.display.BitmapData;
	
	import mx.core.UIComponent;
	
	public class Plot extends UIComponent
	{
		public static const DEFAULT_WID:int  = 300;
		public static const DEFAULT_LEN:int  = 300;
		public static const SCALE_STEP:int   = 50;
		public static const SCALE_COLOR:uint = 0xFF;
		
		public var scaleStep:int = SCALE_STEP;
		public var scaleClr:uint = SCALE_COLOR;
		
		public var lineClr:uint;
		public var multiplier:Number=1;
		public var dc:Number=1;
		protected var bmdSrc:BitmapData;
		
		public function Plot() {
			super();
			this.width  = DEFAULT_WID;
			this.height = DEFAULT_LEN;
			drawGrids();
		}
		
		public function empty():void {
			this.graphics.clear();
		}
		
		protected function drawGrids():void {
			this.graphics.lineStyle(1, SCALE_COLOR);
			for (var y:int=0; y<width; y+=SCALE_STEP) {
				graphics.moveTo(0, y);
				graphics.lineTo(width, y);
			}
			for(var x:int=0; x<height; x+=SCALE_STEP) {
				graphics.moveTo(x, 0);
				graphics.lineTo(x, height);
			}
		}
		
		// analyze the source
		public function set source(bmd:BitmapData):void {
			bmdSrc = bmd;
		}
	}
}