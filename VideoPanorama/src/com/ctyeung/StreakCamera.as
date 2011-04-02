// ==================================================================
// Module:		StreakCamera.as
// 
// Description:	Slit-scanning camera to assemble panoramic photo.
//
// Author(s):	C.T. Yeung
//
// History:
// 10Feb11		1st functional									cty
// 20Feb11		add this comment, 1st publish app 				cty
// ==================================================================
package com.ctyeung
{
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import spark.components.VideoDisplay;

	public class StreakCamera
	{
		public static const SCAN_VERTICAL:String 	= "vertical";
		public static const SCAN_HORIZONTAL:String 	= "horizontal";
		public var streakWidth:int;
		protected var bmdDes:BitmapData;
		protected var bmdSrc:BitmapData;
		protected var direction:String;
		protected var numFrames:int;
		protected var rect:Rectangle;
		protected var p:Point;
		
		public function StreakCamera() {
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
		
		public function resize(videoWidth:int,
							   videoHeight:int,
							   streakWid:int)
							   :Boolean {
			if(bmdDes)
				bmdDes.dispose();
			
			this.streakWidth = streakWid;
			bmdDes = new BitmapData(numFrames*(streakWidth-1)+videoWidth, 
									videoHeight, 
									false);
			return (bmdDes)?true:false;
		}
		
		public function init(videoWidth:int,
							 videoHeight:int,
							 numFrames:int,
							 streakWidth:int,
							 scanDirection:String)
							 :Boolean {
			this.streakWidth = streakWidth;
			this.direction 	 = scanDirection;
			this.numFrames 	 = numFrames;
			bmdSrc 			 = new BitmapData(videoWidth, videoHeight, false);
			
			switch(scanDirection) {
				case SCAN_VERTICAL:
					bmdDes = new BitmapData(videoWidth, 
											numFrames*(streakWidth-1)+videoHeight, 
											false);
					break;
					
				default:
				case SCAN_HORIZONTAL:
					bmdDes = new BitmapData(numFrames*(streakWidth-1)+videoWidth, 
											videoHeight, 
											false);
					break;
			}
			return true;
		}
		
		public function scan(video:VideoDisplay,
							 index:int)
							:Boolean {
			if(!bmdSrc)
				return false;
			
			// actually want to do this with keyframes instead... someday.
			bmdSrc.draw(video);
			setPosition(index);
			bmdDes.copyPixels(bmdSrc, rect, p);
			return true;
		}
		
		protected function setPosition(index:int):void {
			switch(direction) {
				case SCAN_VERTICAL:
					if(index==0) {
						p.x = 0;
						p.y = 0;
						rect = new Rectangle(0, 0, bmdSrc.width, bmdSrc.height/2+streakWidth/2);
					}
					else if(index==(numFrames-1)) {
						p.x = 0;
						p.y = bmdSrc.height/2+index*streakWidth;
						rect = new Rectangle(0, bmdSrc.height/2, bmdSrc.width, bmdSrc.height/2);
					}
					else {
						p.x = 0;
						p.y = bmdSrc.height/2+index*streakWidth;
						rect = new Rectangle(0, bmdSrc.height/2, bmdSrc.width, streakWidth);
					}
					
					p.x = 0; 
					p.y = index * streakWidth;
					rect = new Rectangle(0, bmdSrc.height/2, bmdSrc.width, streakWidth);
					break;
				
				default:
				case SCAN_HORIZONTAL:
					if(index==0) {
						p.x = 0;
						p.y = 0;
						rect = new Rectangle(0, 0, bmdSrc.width/2+streakWidth, bmdSrc.height);
					}
					else if(index==(numFrames-1)) {
						p.x = bmdSrc.width/2+index*streakWidth;
						p.y = 0;
						rect = new Rectangle(bmdSrc.width/2, 0, bmdSrc.width/2, bmdSrc.height);
					}
					else {
						p.x = bmdSrc.width/2+index*streakWidth;
						p.y = 0;
						rect = new Rectangle(bmdSrc.width/2, 0, streakWidth, bmdSrc.height);
					}
					break;
			}
		}
		
		public function get bitmapData():BitmapData {
			return bmdDes;
		}
	}
}