package GradientMethods
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.GraphicsPathCommand;
	import flash.display.GraphicsPathWinding;
	import flash.display.Shader;
	import flash.display.ShaderJob;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.ByteArray;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	public class Triangle extends UIComponent {
		public var ca:uint;
		public var cb:uint;
		public var cc:uint;
		public var num:int = 0;
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
		
		protected function onMouseDown(e:Event):void {
			switch(this.num) {
				case 0:
					pa.x = this.mouseX;
					pa.y = this.mouseY;
					break;
					
				case 1:
					pb.x = this.mouseX;
					pb.y = this.mouseY;
					break;
				
				case 2:
					pc.x = this.mouseX;
					pc.y = this.mouseY;
					break;
			}
			num = (num<2)?num+1:0;
			clear();
			drawTriangle();
		}
		
		public function drawTriangle():void {
			
		}
	}
}