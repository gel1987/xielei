/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\github\\xielei\\app\\android\\app_src\\Donate\\src\\com\\donate\\gf\\PayCallback.aidl
 */
package com.donate.gf;
public interface PayCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.donate.gf.PayCallback
{
private static final java.lang.String DESCRIPTOR = "com.donate.gf.PayCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.donate.gf.PayCallback interface,
 * generating a proxy if needed.
 */
public static com.donate.gf.PayCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.donate.gf.PayCallback))) {
return ((com.donate.gf.PayCallback)iin);
}
return new com.donate.gf.PayCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_paySucess:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.paySucess(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_payFailed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.payFailed(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.donate.gf.PayCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void paySucess(java.lang.String item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(item);
mRemote.transact(Stub.TRANSACTION_paySucess, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void payFailed(java.lang.String item) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(item);
mRemote.transact(Stub.TRANSACTION_payFailed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_paySucess = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_payFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void paySucess(java.lang.String item) throws android.os.RemoteException;
public void payFailed(java.lang.String item) throws android.os.RemoteException;
}
