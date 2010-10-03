// ==================================================================
// Module:		IlluminatiTriangle.as
//
// Description:	I am writing this to drive a pixelbender filter by Illuminati.
//				Unfornately, it does not seem to work correctly even with the help from 
//				the author.  So, this is mostly for comparion with what I have written.
//
// Reference:	http://www.adobe.us/cfusion/exchange/index.cfm?event=extensionDetail&loc=en_us&extid=1937522
//
// Author(s):	Illuminate1989@gmail.com
//				C.T. Yeung		cty
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
	import flash.display.GraphicsPathCommand;
	import flash.display.GraphicsPathWinding;
	import flash.display.Shader;
	import flash.display.ShaderJob;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.ByteArray;
	import flash.utils.getTimer;
	
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
		
		override public function drawTriangle():uint {
			var st:uint = getTimer();
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
			var end:uint = getTimer();
			return end - st;
		}
	}
}