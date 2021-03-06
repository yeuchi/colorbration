// ==================================================================
// Module:			ControlPanelEvent
//
// Description:		event for control panel
//
// Input/Output:	messages
//
// Author(s):		C.T. Yeung
//
// Copyright (c) 2009 C.T.Yeung

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
package events
{
	import flash.events.Event;

	public class ControlPanelEvent extends Event
	{
		public static const CTRL_PANEL_CREATE:String = "controlPanelCreate";
		public static const CTRL_PANEL_DELETE:String = "controlPanelDelete";
		public static const CTRL_PANEL_SOURCE_CHANGE:String = "controlPanelSourceChange";
		public static const CTRL_PANEL_SAMPLE_CHANGE:String = "controlPanelSampleChange";
		public static const CTRL_PANEL_OBSERVER_CHANGE:String = "controlPanelObserverChange";
		
		public var cmbSelectedIndex:int;
		public var cmbSelectedLabel:String;

		public function ControlPanelEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

		override public function clone():Event
		{
			return new ControlPanelEvent(type);
		}
		
	}
}