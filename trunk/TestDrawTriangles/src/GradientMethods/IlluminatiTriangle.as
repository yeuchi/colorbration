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
	
	public class IlluminatiTriangle extends Triangle {
		
		[Embed("assets/triangle-gradient.pbj", mimeType="application/octet-stream")] protected var illuminatiFilter:Class;
		
		public function IlluminatiTriangle(){
			super();
		}
		
		override protected function getShader():Shader {
			return new Shader(new illuminatiFilter());
		}
		
		override public function drawTriangle():void {
			// build path for draw
			cmds = new Vector.<int>();
			data = new Vector.<Number>();
			cmds.push(GraphicsPathCommand.MOVE_TO);
			data.push(pa.x);
			data.push(pa.y);
			
			cmds.push(GraphicsPathCommand.LINE_TO);
			data.push(pb.x);
			data.push(pb.y);
			
			cmds.push(GraphicsPathCommand.LINE_TO);
			data.push(pc.x);
			data.push(pc.y);
			
			// do the pixelbender thing !
			shader = getShader();
			shader.data.pa.value  = [pa.x, pa.y];
			shader.data.pb.value  = [pb.x, pb.y];
			shader.data.pc.value  = [pc.x, pc.y];
			
			var a:Number = 1;
			var r:Number = Number((ca&0xFF0000)>>16)/255.0;
			var g:Number = Number((ca&0xFF00)>>8)/255.0;
			var b:Number = Number(ca&0xFF)/255.0;
			
			shader.data.ca.value  = [a,r,g,b];
			
			a = 1;
			r = Number((cb&0xFF0000)>>16)/255.0;
			g = Number((cb&0xFF00)>>8)/255.0;
			b = Number(cb&0xFF)/255.0;
			
			shader.data.cb.value  = [a,r,g,b];
			
			a = 1;
			r = Number((cc&0xFF0000)>>16)/255.0;
			g = Number((cc&0xFF00)>>8)/255.0;
			b = Number(cc&0xFF)/255.0;
			
			shader.data.cc.value  = [a,r,g,b];
			
			// draw !
			graphics.beginShaderFill(shader);
			graphics.drawPath(cmds,data,GraphicsPathWinding.NON_ZERO);
		}
	}
}