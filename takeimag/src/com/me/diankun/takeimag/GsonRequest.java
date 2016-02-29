package com.me.diankun.takeimag;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

public class GsonRequest<T> extends Request<T> {

	private Response.Listener<T> mListener;
	private Gson mGson;
	private Class mClazz;

	public GsonRequest(String url, ErrorListener erroListener, Listener<T> listener, Class clazz) {
		this(Method.GET, url, erroListener, listener, clazz);

	}

	public GsonRequest(int method, String url, ErrorListener erroListener, Listener<T> listener, Class clazz) {
		super(method, url, erroListener);
		this.mListener = listener;
		mGson = new Gson();
		mClazz = clazz;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return (Response<T>) Response.success(mGson.fromJson(parsed, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

}
