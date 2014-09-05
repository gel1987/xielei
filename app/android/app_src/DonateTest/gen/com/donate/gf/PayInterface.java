/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\github\\xielei\\app\\android\\app_src\\DonateTest\\src\\com\\donate\\gf\\PayInterface.aidl
 */
package com.donate.gf;
public interface PayInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.donate.gf.PayInterface
{
private static final java.lang.String DESCRIPTOR = "com.donate.gf.PayInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.donate.gf.PayInterface interface,
 * generating a proxy if needed.
 */
public static com.donate.gf.PayInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.donate.gf.PayInterface))) {
return ((com.donate.gf.PayInterface)iin);
}
return new com.donate.gf.PayInterface.Stub.Proxy(obj);
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
case TRANSACTION_pay:
{
data.enforceInterface(DESCRIPTOR);
com.donate.gf.PayCallback _arg0;
_arg0 = com.donate.gf.PayCallback.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.pay(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.donate.gf.PayInterface
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
@Override public void pay(com.donate.gf.PayCallback cb, java.lang.String item, int minFee) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
_data.writeString(item);
_data.writeInt(minFee);
mRemote.transact(Stub.TRANSACTION_pay, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_pay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void pay(com.donate.gf.PayCallback cb, java.lang.String item, int minFee) throws android.os.RemoteException;
}
