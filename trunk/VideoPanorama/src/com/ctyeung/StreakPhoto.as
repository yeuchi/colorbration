package com.ctyeung
{
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import spark.components.VideoDisplay;

	public class StreakPhoto
	{
		public static const SCAN_VERTICAL:String 	= "vertical";
		public static const SCAN_HORIZONTAL:String 	= "horizontal";
		protected var bmdDes:BitmapData;
		protected var bmdSrc:BitmapData;
		protected var direction:String;
		protected var streakWidth:int;
		protected var rect:Rectangle;
		protected var p:Point;
		
		public function StreakPhoto()
		{
			p = new Point();
		}
		
		public function dispose():void {
			if(bmdDes) {
				bmdDes.dispose();
				bmdDes = null;
			}
			
			if(bmdSrc) {
				bmdSrc.dispose();
				bmdSrc = null;
			}
			rect = null;
		}
		
		public function get scanLength():int {
			if(!bmdDes)
				return 0;
			
			switch(direction) {
				case SCAN_VERTICAL:
					return bmdDes.height;
					
				default:
				case SCAN_HORIZONTAL:
					return bmdDes.width;
			}
		}
		
		public function init(videoWidth:int,
							 videoHeight:int,
							 numFrames:int,
							 streakWidth:int,
							 scanDirection:String)
							 :Boolean {
			this.streakWidth = streakWidth;
			this.direction = scanDirection;
			bmdSrc = new BitmapData(videoWidth, videoHeight, false);
			switch(scanDirection) {
				case SCAN_VERTICAL:
					bmdDes = new BitmapData(videoWidth, numFrames*streakWidth, false);
					break;
					
				default:
				case SCAN_HORIZONTAL:
					bmdDes = new BitmapData(numFrames*streakWidth, videoHeight, false);
					break;
			}
			return true;
		}
		
		public function scan(video:VideoDisplay,
							 index:int)
							:Boolean {
			if(!bmdSrc)
				return false;
			
			bmdSrc.draw(video);
			setPosition(index);
			bmdDes.copyPixels(bmdSrc, rect, p);
			return true;
		}
		
		protected function setPosition(index:int):void {
			switch(direction) {
				case SCAN_VERTICAL:
					p.x = 0; 
					p.y = index * streakWidth;
					rect = new Rectangle(0, index, bmdSrc.width, streakWidth);
					break;
				
				default:
				case SCAN_HORIZONTAL:
					p.x = index * streakWidth;
					p.y = 0;
					rect = new Rectangle(index, 0, streakWidth, bmdSrc.height);
					break;
			}
		}
		
		public function get bitmapData():BitmapData {
			return bmdDes;
		}
	}
}