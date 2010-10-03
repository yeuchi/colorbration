// ==================================================================
// Module:		Triangle.as
//
// Description:	This is the base class for my triangle algorithm test
//				experiment.
//
// Author(s):	C.T. Yeung		cty
//
// History:
// 15Sep10		first started									cty
//
// Copyright (c) 2009 C.T.Yeung
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:

// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
// CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
// TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
// SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// ==================================================================
package GradientMethods
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.GraphicsPathCommand;
	import flash.display.GraphicsPathWinding;
	import flash.display.Shader;
	import flash.display.ShaderJob;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.ByteArray;
	
	import mx.controls.Label;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	public class Triangle extends UIComponent {
		public var ca:uint;
		public var cb:uint;
		public var cc:uint;
		protected var cmds:Vector.<int>;
		protected var data:Vector.<Number>;
		protected var shader:Shader;
		
		protected var pbFilter:Class;
		protected var bDown:Boolean;
		protected var pa:Point;
		protected var pb:Point;
		protected var pc:Point;
		
		public function Triangle(){
			super();
			this.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown, false, 0, true);
			this.addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete, false, 0, true);
		}
		
		public function dispose():void {
			shader = null;
			cmds = null;
			data = null;
		}
		
		public function clear():void {
			this.graphics.clear();
			this.graphics.beginFill(0xEEEEEE, .2);
			this.graphics.drawRect(0,0,500,500);
		}
		
		protected var timeField:Label;
		public function set label(obj:Label):void {
			timeField = obj;
		}
		
		protected function getShader():Shader {
			return null;
		}
		
		protected function onCreationComplete(e:Event):void {
			pa = new Point(250, 1);
			pb = new Point(1, 499);
			pc = new Point(499,499);
			ca = 0xFFFF0000;
			cb = 0xFF00FF00;
			cc = 0xFF0000FF;
			clear();
		}
		
		protected function onMouseDown(e:MouseEvent):void {
			var p:Point = new Point(e.localX, e.localY);
			var da:Number = Point.distance(p,pa);
			var db:Number = Point.distance(p,pb);
			var dc:Number = Point.distance(p,pc);
			
			if(da<db&&da<dc) {
				pa.x = this.mouseX;
				pa.y = this.mouseY;
			}
			else if (db<da&&db<dc) {
				pb.x = this.mouseX;
				pb.y = this.mouseY;
			}
			else {
				pc.x = this.mouseX;
				pc.y = this.mouseY;
			}
			clear();
			var t:uint = drawTriangle();
			timeField.text = "Elapsed: "+ t.toString();
		}
		
		public function drawTriangle():uint {
			return 0;
		}
	}
}