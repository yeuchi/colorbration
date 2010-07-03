package Views
{
	import flash.display.BitmapData;
	import flash.geom.Point;
	
	import mx.core.UIComponent;
	
	public class Plot extends UIComponent
	{
		public static const DEFAULT_WID:int  = 300;
		public static const DEFAULT_LEN:int  = 300;
		public static const SCALE_STEP:int   = 50;
		public static const SCALE_COLOR:uint = 0xFF;
		public static const VALUE_COLOR:uint = 0x88888888;
		public static const DEFAULT_PIXEL_RATE:int 	 = 15;
		public static const DEFAULT_SAMPLE_RATE:int = 24;
		
		public static const MODE_VALUE:String  = "MODE_VALUE";
		public static const MODE_CHROMA:String = "MODE_CHROMA";

		public var mode:String = MODE_VALUE;
		public var scaleStep:int = SCALE_STEP;
		public var scaleClr:uint = SCALE_COLOR;
		
		public var lineClr:uint;
		public var dcValue:Number=1;
		public var ampValue:Number=1;
		public var pixelRate:int = DEFAULT_PIXEL_RATE;
		protected var ptMid:Point;
		
		protected var bmdSrc:BitmapData;
		
		public function Plot() {
			super();
			this.width  = DEFAULT_WID;
			this.height = DEFAULT_LEN;
			drawGrids();
			
			ptMid = new Point(Number(width/2.0), Number(height/2.0));
			lineClr = VALUE_COLOR;
			dcValue = height/2;
			ampValue = height *5 / 255.0;
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
			this.graphics.clear();
			this.drawGrids();
			invalidate();
		}
		
		public function invalidate():void {
			switch(mode) {
				case MODE_CHROMA:
					renderColor();
					break;
				
				case MODE_VALUE:
					renderValue();
					break;
			}
		}
		
		protected function renderColor():void {}
		protected function renderValue():void {}
	}
}