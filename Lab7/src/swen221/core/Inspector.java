// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import swen221.util.Rectangle;

/**
 * Responsible for interacting with widgets via reflection. This includes
 * reading and writing attributes on the widgets, and creating instances of
 * them.
 * 
 * @author David J. Pearce
 *
 */
public class Inspector {
	/**
	 * Provides the master list of widgets from which we can create new widgets
	 */
	private Class<? extends Widget>[] widgets;

	public Inspector(Class<? extends Widget>... widgets) {
		this.widgets = widgets;
	}

	/**
	 * Return the array of all valid widgets being considered by this inspector.
	 * 
	 * @return
	 */
	public Class<? extends Widget>[] getWidgets() {
		return widgets;
	}

	/**
	 * Construct a new widget of a particular kind with given dimensions.
	 * 
	 * @param name
	 *            The kind of widget to create
	 * @param dimensions
	 *            The dimensions of the widget
	 * @return
	 */
	public Widget newWidget(String name, Rectangle dimensions) {
		Class<?> widgetClass = findWidget(name);
		// HINT: You should look in the widget class (using reflection) for a
		// constructor which accepts a Rectangle object as a parameter. Then you
		// can just invoke this method passing in the dimensions parameter.
		try {
			return (Widget) widgetClass.getConstructor(Rectangle.class).newInstance(dimensions);
		}
		catch(IllegalArgumentException e) {
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	/**
	 * Get the value of a given attribute for this widget. This is done my
	 * calling the corresponding (public) getter method.
	 * 
	 * @param widget
	 *            The widget whose attribute we wish to read
	 * @param name
	 *            The name of the attribute to be read
	 * 
	 * @param name
	 * @return
	 */
	public Object getAttributeValue(Widget widget, String name) {
		// To get the value of a given attribute in the widget, you need to use
		// its corresponding "getter" method. For example, for an attribute
		// "color" the getter would be "getColor".
		String getterName = "get" + capitalise(name);
		// What you need to do is find the appropriate getter method and invoke
		// it. This doesn't accept any parameters. Furthermore, you should just
		// through an IllegalArgumentException for all the various error cases
		// which arise when doing this (SecurityException,
		// NoSuchMethodException, etc).
		try {
			return widget.getClass().getMethod(getterName).invoke(widget);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * Set the value of a given attribute for this widget
	 * 
	 * @param widget
	 *            The widget whose attribute we wish to update
	 * @param name
	 *            The name of the attribute to be updated
	 * @param value
	 *            The value of the attribute to be updated
	 */
	public void setAttribute(Widget widget, String name, Object value) {
		// To set the value of a given attribute in the widget, you need to use
		// its corresponding "setter" method. For example, for an attribute
		// "color" the getter would be "SetColor".
		String setterName = "set" + capitalise(name);
		// As before, you need to first find the setter method. This is harder
		// than before because you need to know the type of the attribute being
		// set. To work this out, you need to get the "type" of the corresponding
		// field using Class.getDeclaredField().
		Class<? extends Widget> widgetClass= widget.getClass();
		Field field=null;
		try {
			field=widgetClass.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Method method=null;
		
			try {
				method=widgetClass.getDeclaredMethod(setterName, field.getType());
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				method.invoke(widget, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * Make the first letter of the string a captial.
	 * 
	 * @param str
	 * @return
	 */
	private static String capitalise(String str) {
		String rest = str.substring(1);
		char c = Character.toUpperCase(str.charAt(0));
		return c + rest;
	}

	private Class<? extends Widget> findWidget(String widgetName) {
		for (Class<? extends Widget> w : widgets) {
			if (w.getSimpleName().equals(widgetName)) {
				return w;
			}
		}
		throw new IllegalArgumentException("Widget " + widgetName + " does not exist");
	}
}
