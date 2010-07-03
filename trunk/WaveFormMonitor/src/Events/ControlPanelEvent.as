package Events
{
	import flash.events.Event;

	public class ControlPanelEvent extends Event
	{
		public static const DC_CHANGED:String 	 		= "dcValueChanged";
		public static const AMP_CHANGED:String   		= "ampValueChanged";
		public static const MODE_CHANGED:String  		= "modeChanged";
		public static const SPACE_CHANGED:String 		= "spaceChanged";
		public static const FRAME_RATE_CHANGED:String 	= "frameRateChanged";
		public static const PIXEL_RATE_CHANGED:String 	= "pixelRateChanged";
		
		public var value:Number;
		public var mode:String;
		public var space:String;

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