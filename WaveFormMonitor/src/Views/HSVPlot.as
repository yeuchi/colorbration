package Views
{
	import flash.display.BitmapData;
	import flash.geom.Point;
	
	import mx.core.UIComponent;
	
	public class HSVPlot extends Plot
	{
		public static const SPACE:String = "SPACE_HSV";
		protected var convert:RGB2HSV;
		
		public function HSVPlot()
		{
			super();
			ptMid = new Point(Number(width/2.0), Number(height/2.0));
			convert = new RGB2HSV();
		}
		
//		override protected function renderColor():void {
//			var last:Date = new Date();
//			var bmd:BitmapData = convert.apply(bmdSrc);
//			if(!bmd) {
//				trace("HSVPlot.renderColor() convert.apply() failed");
//				return;
//			}
//			
//			for (var y:int=0; y<bmd.height; y+=pixelRate) {
//				for(var x:int=0; x<bmd.height; x+=pixelRate) {
//					var clr:uint = bmd.getPixel(x,y);
//					var pt:Point = Point.polar(((clr&0xFF00)>>8)/255.0*ptMid.x, 
//												2*Math.PI*(((clr&0xFF0000)>>16)/255.0));
//					this.graphics.lineStyle(1, clr);
//					this.graphics.drawCircle(pt.x+ptMid.x, pt.y+ptMid.y,1);
//					this.graphics.endFill();
//				}
//			}
//			graphics.endFill();
//			var now:Date = new Date();
//			trace ("rendered:"+(now.time-last.time).toString());
//		}
		
//		override protected function renderValue():void {
//			var last:Date = new Date();
//			var bmd:BitmapData = convert.apply(bmdSrc);
//			if(!bmd) {
//				trace("HSVPlot.renderColor() convert.apply() failed");
//				return;
//			}
//			this.graphics.lineStyle(1, lineClr);
//			var stepX:Number = Number(bmdSrc.width)/Number(this.width);
//			var amp:Number;
//			
//			for (var y:int=0; y<bmd.height; y+=pixelRate) {
//				this.graphics.moveTo(0, this.height-dcValue);
//				for(var x:int=0; x<bmd.height; x+=pixelRate) {
//					var clr:uint = bmd.getPixel(int(x*stepX),y);
//					amp = (clr&0xFF)*ampValue+dcValue;
//					if(amp>this.height)
//						amp = this.height;
//					else if(amp < 0)
//						amp = 0;
//					this.graphics.lineTo(x, amp);
//				}
//			}
//			graphics.endFill();
//			var now:Date = new Date();
//			trace ("rendered:"+(now.time-last.time).toString());
//		}
		
		override protected function renderColor():void {
			// need to debug
			var last:Date = new Date();
			
			for(var y:int=0; y<bmdSrc.height; y+=pixelRate) {
				var clr:uint = bmdSrc.getPixel(0,y);
				convert.forward(clr);
				var pt:Point = Point.polar(convert.s, convert.h);
				
				for(var x:int=1; x<bmdSrc.width; x+=pixelRate) {
					// 1st point
					clr = bmdSrc.getPixel(x,y);
					convert.forward(clr);
					var pt2:Point = Point.polar(convert.s*ptMid.x, 2*Math.PI*(convert.h/360));
					this.graphics.lineStyle(1, clr);
					this.graphics.drawCircle(pt2.x+ptMid.x, pt2.y+ptMid.y,1);
					this.graphics.endFill();
					pt = pt2;					
				}
			}
			graphics.endFill();
		var now:Date = new Date();
		trace ("rendered:"+(now.time-last.time).toString());
		}
		
		override protected function renderValue():void {
		var last:Date = new Date();
			this.graphics.lineStyle(1, lineClr);
			var stepX:Number = Number(bmdSrc.width)/Number(this.width);
			var amp:Number;
			
			for(var y:int=0; y<bmdSrc.height; y+=pixelRate) {
				this.graphics.moveTo(0, this.height-dcValue);
				for(var x:int=0; x<this.width; x+=pixelRate) {
					var clr:uint = bmdSrc.getPixel(int(x*stepX),y);
					convert.forwardValue(clr);
					amp = convert.v*255*ampValue+dcValue;
					if(amp>this.height)
						amp = this.height;
					else if(amp < 0)
						amp = 0;
					this.graphics.lineTo(x, amp);
				}
			}
			graphics.endFill();
			var now:Date = new Date();
			trace ("rendered:"+(now.time-last.time).toString());
		}
	}
}