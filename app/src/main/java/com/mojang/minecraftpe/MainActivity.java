package com.mojang.minecraftpe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.content.ClipData;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.app.NativeActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;

import org.fmod.FMOD;
import org.tlauncher.tlauncherpe.mc.McVersion;

import com.byteandahalf.genericlauncher.*; // import ALL the things
import com.byteandahalf.genericlauncher.NativeHandler;
import com.mcbox.pesdk.archive.entity.Options;
import com.mcbox.pesdk.archive.util.OptionsUtil;
import com.mcbox.pesdk.launcher.LauncherConstants;
import com.mcbox.pesdk.mcfloat.util.LauncherUtil;
import com.mcbox.pesdk.util.McInstallInfoUtil;
import com.umeng.analytics.MobclickAgent;
import com.yy.hiidostatis.api.HiidoSDK;
import com.yy.hiidostatis.defs.obj.Property;
import com.yy.hiidostatis.inner.BaseStatisContent;

import static com.mcbox.pesdk.mcfloat.util.LauncherUtil.getPrefs;


public class MainActivity extends NativeActivity {

	public static final int UI_HOVER_BUTTON_TEST = 0;
	List<ActivityListener> mActivityListeners = new ArrayList();
	public static int RESULT_GOOGLEPLAY_PURCHASE = 2;

	PackageInfo packageInfo;
	ApplicationInfo appInfo;
	String libraryDir;
	String libraryLocation;
	boolean canAccessAssets = false;
	Context apkContext = null;
	DisplayMetrics metrics;

	boolean mcpePackage = false;

	public static ByteBuffer minecraftLibBuffer = null;
	public static MainActivity activity = null;

	static {
	    System.loadLibrary("gnustl_shared");
    }

	public void m4878a(String str,String tr, String strArr) {
		m4877a(activity, str, strArr);
	}

	public void m4877a(Context context, String str, String... strArr) {
		if (strArr == null || strArr.length <= 0) {
			MobclickAgent.onEvent(context, str);
			HiidoSDK.instance().reportTimesEvent(0, str, null);
		} else if (strArr.length >= 2 && strArr.length % 2 == 0) {
			HashMap hashMap = new HashMap();
			int i = 0;
			while (i < strArr.length) {
				hashMap.put(strArr[i], strArr[i + 1]);
				i += 2;
			}
			m4875a(context, str, null, hashMap);
		}
	}

	public void m4875a(Context context, String str, String str2, Map<String, String> map) {
		/*MobclickAgent.onEvent(context, str, map);
		Property property = new Property();
		for (String str3 : map.keySet()) {
			property.putString(str3, (String) map.get(str3));
		}
		//C0894a.m4874a(context, str, str2, property)
		HiidoSDK.instance().reportTimesEvent(0, str, str2, property);*/
		MobclickAgent.onEvent(context, str, (Map) map);
		Property property = new Property();
		for (String str3 : map.keySet()) {
			property.putString(str3, (String) map.get(str3));
		}
		HiidoSDK.instance().reportTimesEvent(0, str, str2, property);
	}

	public void importInGame(String str, Activity activity){
		String st = str;
		if (st == null) {
			try {
				st = "";
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		getPrefs(1).edit().putString(LauncherConstants.PREF_KEY_SKIN_PLAYER, st).apply();
		McVersion fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity));
		if (fromVersionString.getMajor().intValue() >= 0 && fromVersionString.getMinor().intValue() >= 0) {
			killMCProgress(activity);
			File c;
			Options options;
			if (st == null || st.trim().length() <= 0) {
				c = m4930c();
				if (c.exists()) {
					c.delete();
				}
				try {
					options = OptionsUtil.getInstance().getOptions();
					options.setGame_skintype(0);
					options.setGame_lastcustomskin(0);
					options.setGame_skintypefull(Options.SKIN_TYPE_Steve);
					options.setGame_lastcustomskinnew(Options.SKIN_TYPE_Steve);
					OptionsUtil.getInstance().writeOptions(options);
					return;
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			}
			c = new File(st);
			if (c.exists()) {
				try {
					copyFile(c, m4930c());
					options = OptionsUtil.getInstance().getOptions();
					options.setGame_skintype(0);
					options.setGame_lastcustomskin(0);
					options.setGame_skintypefull(Options.SKIN_TYPE_Custom);
					options.setGame_lastcustomskinnew(Options.SKIN_TYPE_Custom);
					OptionsUtil.getInstance().writeOptions(options);
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			}
		}
	}

	public void killMCProgress(Activity activity) {
		if (isMcRunning(activity)) {
			McInstallInfoUtil.killMc(activity);
		}
	}

	public boolean isMcRunning(Activity activity){
		try {
			ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
			if (activityManager != null) {
				List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
				if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
					for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
						if (McInstallInfoUtil.mcPackageName.equals(runningAppProcessInfo.processName)) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void copyFile(File file, File file2) {
		int i = 0;
		try {
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				FileOutputStream fileOutputStream = new FileOutputStream(file2);
				byte[] bArr = new byte[1444];
				while (true) {
					int read = fileInputStream.read(bArr);
					if (read != -1) {
						i += read;
						fileOutputStream.write(bArr, 0, read);
					} else {
						fileInputStream.close();
						return;
					}
				}
			}
		} catch (Exception e) {
			//println("Coping file error!");
			e.printStackTrace();
		}

	}

	public File m4930c() {
		return new File("file://"+Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/custom.png");
	}

	public void m4876a(Context context, String str, String  str2, boolean z) {
		//if (!f2943b.booleanValue()) {
		String str3 = "";
		str3 = "";
		if (str.contains("/")) {
			String substring = str.substring(0, str.indexOf("/"));
			str3 = str.substring(str.indexOf("/") + 1);
			HashMap hashMap = new HashMap();
			hashMap.put("parm1", str3);
			hashMap.put("parm2", str2);
			MobclickAgent.onEvent(context, substring, hashMap);
			Property property = new Property();
			property.putString("parm1", str3);
			property.putString("parm2", str2);
			HiidoSDK.instance().reportTimesEvent(0, substring, null, property);
                /*if (z && f2942a != null) {
                    f2942a.m1902a(C0575m(str, str).m1843c(str2).mo1835a())
                    return
                }*/
			return;
		}
		//C0894a.m4881b(context, str)
		MobclickAgent.onEvent(context, str);
		HiidoSDK.instance().reportTimesEvent(0, str, null);
		//}
	}

	public Set<String> getEnabledScripts(){
		String string = getPrefs(1).getString("enabledScripts", "");
		return string.equals("") ? new HashSet() : new HashSet(Arrays.asList(string.split(";")));
	}

	public void startMC(Activity activity, Boolean z, Boolean z2) {
		try {
			//if (checkMcpeInstalled(activity)) {
			m4876a(activity, "start_mc_btn", "",false);
			McVersion fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity));
			if (fromVersionString.getMajor().intValue() > 0 || fromVersionString.getMinor().intValue() >= 12) {
				int size = getEnabledScripts().size();
				boolean z3 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, false);
				boolean z4 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_TEXTURE_ENABLE, false);
				boolean floatingWindowStatue = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("setFloatingWindowStatue", true);
				boolean pluginEnable = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("pluginEnable", false);
				if (fromVersionString.getMajor().intValue() <= 0 && fromVersionString.getMinor().intValue() <= 9) {
					startMcWithCleanMem(activity, 1);
					return;
				} else if (fromVersionString.getMajor().intValue() <= 0 && fromVersionString.getMinor().intValue() == 12 && fromVersionString.getBeta().intValue() > 0 && fromVersionString.getBeta().intValue() < 6) {
					startMcWithCleanMem(activity, 1);
					return;
				} else if (floatingWindowStatue) {
					startMcWithCleanMem(activity, 2);
					return;
				} else if (z3 || z4 || pluginEnable || size != 0) {
					startMcWithCleanMem(activity, 2);
					if (z3 && size > 0) {
						MobclickAgent.onEvent(activity, "start_mc_skin_js");
						HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin_js", null);
						return;
					} else if (z3) {
						MobclickAgent.onEvent(activity, "start_mc_skin");
						HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin", null);
						return;
					} else if (size > 0) {
						MobclickAgent.onEvent(activity, "start_mc_js");
						HiidoSDK.instance().reportTimesEvent(0, "start_mc_js", null);
						return;
					} else {
						return;
					}
				} else {
					startMcWithCleanMem(activity, 1);
					return;
				}
			}
			startMcWithCleanMem(activity, 1);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startMcWithCleanMem(Activity activity, int num) {
        /*if (if (2 === num) cleanTextureAndPlugin(activity) else true) {
            PrefUtil.setWorldLocation(activity, "")
            if (LauncherUtil.getPrefs(0)!!.getBoolean("isEnableMemClean", true)) {
                val intent = Intent(activity, MemoryCleanActivity::class.java)
                intent.putExtra("startType", num)
                activity.startActivity(intent)
            } else if (num == 1) {*/
		//startPlug(activity)
		//activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(McInstallInfoUtil.mcPackageName));
            /*} else {
                startPlug(activity)
            }
        }*/
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		activity = this;
		LauncherUtil.init(activity);
		PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, true).apply();
		m4878a("apply_skin", BaseStatisContent.FROM, "我的皮肤列表应用皮肤");
		importInGame(Environment.getExternalStorageDirectory().toString()+ "/games/com.mojang/skin_packs/custom.png", activity);
		m4878a("start_mcpe", BaseStatisContent.FROM, "我的皮肤列表开启游戏");
		startMC(activity, true, false);

		try {
			packageInfo = getPackageManager().getPackageInfo(
					"com.mojang.minecraftpe", 0);
			appInfo = packageInfo.applicationInfo;
			libraryDir = appInfo.nativeLibraryDir;
			libraryLocation = libraryDir + "/libminecraftpe.so";
			System.out.println("libminecraftpe.so is located at " + libraryDir);
			canAccessAssets = !appInfo.sourceDir
					.equals(appInfo.publicSourceDir);
			// int minecraftVersionCode = packageInfo.versionCode;

			if (this.getPackageName().equals("com.mojang.minecraftpe")) {
				apkContext = this;
			} else {
				apkContext = createPackageContext("com.mojang.minecraftpe",
						Context.CONTEXT_IGNORE_SECURITY);
			}
			Log.d("GenericLauncher","Load library fmod !");
			System.load(libraryDir+"/libfmod.so");
			Log.d("GenericLauncher","Load minecraftpe !");

			System.load(libraryLocation);

			FMOD.init(this);
			if(!FMOD.checkInit()) {
				Log.e("GenericLauncher","Failed to init fmod");
			} else {
			//Log.d("GenericLauncher","fmod init sucessful");
			}

			metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			addLibraryDirToPath(libraryDir);
			mcpePackage = true;
			super.onCreate(savedInstanceState);

			try {
				NativeHandler.init();

			} catch(Exception e) {
				e.printStackTrace();
			}
			
			getWindow().getDecorView().post(new Runnable() {
				public void run() {
					setupTestButton();
				}
			});

			nativeRegisterThis();
			mcpePackage = false;

			int flag = VERSION.SDK_INT >= 19 ? 0x40000000 : 0x08000000; // FLAG_NEEDS_MENU_KEY
			getWindow().addFlags(flag);
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}

	}

	public void stopTextToSpeech() {
		/*if (this.textToSpeechManager != null) {
			this.textToSpeechManager.stop();
		}*/
	}

	public boolean isTextToSpeechInProgress() {
		/*if (this.textToSpeechManager != null) {
			return this.textToSpeechManager.isSpeaking();
		}*/
		return false;
	}

	public void trackPurchaseEvent(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
		/*HashMap localHashMap = new HashMap();
		localHashMap.put("player_session_id", paramString5);
		localHashMap.put("client_id", paramString4);
		localHashMap.put("af_revenue", paramString3);
		localHashMap.put("af_content_type", paramString2);
		localHashMap.put("af_content_id", paramString1);
		AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), "af_purchase", localHashMap);*/
	}

	void setFileDialogCallback(long paramLong) {
		//is.mFileDialogCallback = paramLong;
	}

	public String createDeviceID() {
		SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Object localObject2 = localSharedPreferences.getString("snooperId", "");
		Object localObject1 = localObject2;
		if (((String)localObject2).isEmpty()) {
			localObject1 = UUID.randomUUID().toString().replaceAll("-", "");
			localObject2 = localSharedPreferences.edit();
			((SharedPreferences.Editor)localObject2).putString("snooperId", (String)localObject1);
			((SharedPreferences.Editor)localObject2).commit();
		}
		return (String)localObject1;
	}

	public void requestStoragePermission(int paramInt) {
		/*this.mLastPermissionRequestReason = paramInt;
		ActivityCompat.requestPermissions(this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1);*/
	}

	public void setTextToSpeechEnabled(boolean paramBoolean) {
		/*if ((!paramBoolean) || (this.textToSpeechManager == null)) {}
		try {
			this.textToSpeechManager = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
				public void onInit(int paramAnonymousInt) {}
			});
			return;
		}
		catch (Exception localException) {}
		this.textToSpeechManager = null;
		return;*/
	}

	public boolean hasWriteExternalStoragePermission() {
		/*if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {}
		for (boolean bool = true;; bool = false) {
			mHasStoragePermission = bool;
			return mHasStoragePermission;
		}*/
		return true;
	}

	public void updateLocalization(final String paramString1, final String paramString2) {
		runOnUiThread(new Runnable() {
			public void run() {
				Locale localLocale = new Locale(paramString1, paramString2);
				Locale.setDefault(localLocale);
				Configuration localConfiguration = new Configuration();
				localConfiguration.locale = localLocale;
				MainActivity.this.getResources().updateConfiguration(localConfiguration, MainActivity.this.getResources().getDisplayMetrics());
			}
		});
	}

	public int getCursorPosition() {
		/*if ((this.mHiddenTextInputDialog == null) || (this.textInputWidget == null)) {
			return -1;
		}
		return this.textInputWidget.getSelectionStart();*/
		return 1;
	}

	public String[] getIPAddresses() {
		/*localArrayList = new ArrayList();
		try {
			System.setProperty("java.net.preferIPv4Stack", "true");
			Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
			while (localEnumeration.hasMoreElements()) {
				Object localObject = (NetworkInterface)localEnumeration.nextElement();
				if ((!((NetworkInterface)localObject).isLoopback()) && (((NetworkInterface)localObject).isUp())) {
					localObject = ((NetworkInterface)localObject).getInterfaceAddresses().iterator();
					while (((Iterator)localObject).hasNext()) {
						InterfaceAddress localInterfaceAddress = (InterfaceAddress)((Iterator)localObject).next();
						InetAddress localInetAddress = localInterfaceAddress.getAddress();
						if ((localInetAddress != null) && (!localInetAddress.isAnyLocalAddress()) && (!localInetAddress.isMulticastAddress()) && (!localInetAddress.isLinkLocalAddress())) {
							localArrayList.add(localInterfaceAddress.getAddress().toString().substring(1));
						}
					}
				}
			}
			return (String[])localArrayList.toArray(new String[localArrayList.size()]);
		}
		catch (Exception localException) {}*/
		return new String[0];
	}

	public long getUsedMemory() {
		return Debug.getNativeHeapAllocatedSize();
	}

	public long getAvailableMemory() {
		ActivityManager localActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
		localActivityManager.getMemoryInfo(localMemoryInfo);
		return localMemoryInfo.availMem;
	}

	public void setClipboard(String paramString) {
		/*paramString = ClipData.newPlainText("MCPE-Clipdata", paramString);
		this.clipboardManager.setPrimaryClip(paramString);*/
	}

	public Intent createAndroidLaunchIntent() {
		Context localContext = getApplicationContext();
		return localContext.getPackageManager().getLaunchIntentForPackage(localContext.getPackageName());
	}

	public long calculateAvailableDiskFreeSpace(String paramString) {
		/*paramString = new StatFs(paramString);
		if (Build.VERSION.SDK_INT >= 18) {
			return paramString.getAvailableBytes();
		}
		return paramString.getAvailableBlocks() * paramString.getBlockSize();*/
		return 0;
	}

	public void startTextToSpeech(String paramString) {
		/*if (this.textToSpeechManager != null) {
			this.textToSpeechManager.speak(paramString, 0, null);
		}*/
	}

	public String getExternalStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public String getLocale() {
		return HardwareInformation.getLocale();
	}

	public int getAndroidVersion() {
		return VERSION.SDK_INT;
	}

	boolean hasHardwareChanged() {
		return false;
	}

	boolean isTablet() {
		return (getResources().getConfiguration().screenLayout & 0xF) ==4;
	}

	public boolean isFirstSnooperStart() {
		return PreferenceManager.getDefaultSharedPreferences(this).getString("snooperId","").isEmpty();
	}

	public String createUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public String getDeviceId() {
		SharedPreferences sharedPreferences = 
			PreferenceManager.getDefaultSharedPreferences(this);
		String id = sharedPreferences.getString("snooperId", "");
		if (id.isEmpty()) {
			id = createUUID();
			SharedPreferences.Editor edit=sharedPreferences.edit();
			edit.putString("snooperId",id);
			edit.commit();
		}
		return id;
	}


	public String getDeviceModel() {
		String str1 = Build.MANUFACTURER;
		String str2 = Build.MODEL;
		if (str2.startsWith(str1))
		return str2.toUpperCase();
		return str1.toUpperCase() + " " + str2;
	}

	private File[] addToFileList(File[] files, File toAdd) {
		for (File f : files) {
			if (f.equals(toAdd)) {
				// System.out.println("Already added path to list");
				return files;
			}
		}
		File[] retval = new File[files.length + 1];
		System.arraycopy(files, 0, retval, 1, files.length);
		retval[0] = toAdd;
		return retval;
	}

	public static Field getDeclaredFieldRecursive(Class<?> clazz, String name) {
		if (clazz == null)
			return null;
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException nsfe) {
			return getDeclaredFieldRecursive(clazz.getSuperclass(), name);
		}
	}

	private void addLibraryDirToPath(String path) {
		try {
			ClassLoader classLoader = getClassLoader();
			Class<? extends ClassLoader> clazz = classLoader.getClass();
			Field field = getDeclaredFieldRecursive(clazz, "pathList");
			field.setAccessible(true);
			Object pathListObj = field.get(classLoader);
			Class<? extends Object> pathListClass = pathListObj.getClass();
			Field natfield = getDeclaredFieldRecursive(pathListClass,
					"nativeLibraryDirectories");
			natfield.setAccessible(true);
			File[] fileList = (File[]) natfield.get(pathListObj);
			File[] newList = addToFileList(fileList, new File(path));
			if (fileList != newList)
				natfield.set(pathListObj, newList);
			// check
			// System.out.println("Class loader shenanigans: " +
			// ((PathClassLoader) getClassLoader()).findLibrary("minecraftpe"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PackageManager getPackageManager() {
		if (mcpePackage) {
			return new RedirectPackageManager(super.getPackageManager(), libraryDir);
		}
		return super.getPackageManager();
	}

	public native void nativeRegisterThis();

	public native void nativeUnregisterThis();

	public native void nativeTypeCharacter(String character);

	public native void nativeSuspend();

	public native void nativeSetTextboxText(String text);

	public native void nativeBackPressed();

	public native void nativeBackSpacePressed();

	public native void nativeReturnKeyPressed();

	public void buyGame() {
	}

	public int checkLicense() {
		return 0;
	}

	public void onBackPressed() {}

	public void displayDialog(int dialogId) {
		Log.d("GenericLauncher", "[displayDialog] Dialog ID:" + dialogId);
	}

	public String getDateString(int time) {
		System.out.println("getDateString: " + time);
		return DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(
				new Date(((long) time) * 1000));
	}

	public byte[] getFileDataBytes(String name) {
		System.out.println("Get file data: " + name);
		try {
			if (name.isEmpty())
				return null;
			InputStream is = null;
			if (name.charAt(0) == '/')
				is = new FileInputStream(new File(name));
			else 
				is = getInputStreamForAsset(name);
			if (is == null) {
				Log.e("GenericLauncher", 
					"FILE IS NULL!");
				return null;
			}
			// can't always find length - use the method from
			// http://www.velocityreviews.com/forums/t136788-store-whole-inputstream-in-a-string.html
			// instead
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while (true) {
				int len = is.read(buffer);
				if (len < 0) {
					break;
				}
				bout.write(buffer, 0, len);
			}
			byte[] retval = bout.toByteArray();

			return retval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//public ArrayList<TexturePack> texturePacks = new ArrayList<TexturePack>();

	protected InputStream getInputStreamForAsset(String name) {
		try {

			/*for (int i = 0; i < texturePacks.size(); i++) {
				try {
					InputStream is = texturePacks.get(i).getInputStream(name);
					if (is != null)
						return is;
				} catch (IOException e) {
				}
			}*/

			return getLocalInputStreamForAsset(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected InputStream getLocalInputStreamForAsset(String name) {
		//Log.d("GenericLauncher","getStreamAsset "+name);
		InputStream is = null;
		try {
			/*
			 * if (forceFallback) { return getAssets().open(name); }
			 */
			try {
				if (name.charAt(0) == '/')
					is =new FileInputStream(new File(name));
				else 
					is = apkContext.getAssets().open(name);
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("Attempting to load fallback");
				is = getAssets().open(name);
			}
			if (is == null) {
				System.out.println("Can't find it in the APK");
				is = getAssets().open(name);
			}
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int[] getImageData(String paramString){
		/*Bitmap localBitmap = BitmapFactory.decodeFile(paramString);
		Object localObject = localBitmap;
		if (localBitmap == null) {
			localObject = getApplicationContext();
		}
		try {
			localObject = ((Context)localObject).getAssets();
			if (localObject != null) {
				try {
					localObject = ((AssetManager)localObject).open(paramString);
					localObject = BitmapFactory.decodeStream((InputStream)localObject);
					int i = ((Bitmap)localObject).getWidth();
					int j = ((Bitmap)localObject).getHeight();
					paramString = new int[i * j + 2];
					paramString[0] = i;
					paramString[1] = j;
					((Bitmap)localObject).getPixels(paramString, 2, i, 0, 0, i, j);
					return paramString;
				}catch (IOException localIOException) {
					System.err.println("getImageData: Could not open image " + paramString);
					return null;
				}
			}
			System.err.println("getAssets returned null: Could not open image " + paramString);
		}catch (NullPointerException localNullPointerException) {
			System.err.println("getAssets threw NPE: Could not open image " + paramString);
			return null;
		}
		return null;*/
		return  new int[]{};
	}

	public String getFormattedDateString(int paramInt) {
		/*DateFormat localDateFormat = this.DateFormat;
		localDateFormat = this.DateFormat;
		return DateFormat.getDateInstance(3, this.initialUserLocale).format(new Date(paramInt * 1000L));*/
		return "";
	}

	public String getFileTimestamp(int paramInt) {
		return new SimpleDateFormat("__EEE__yyyy_MM_dd__HH_mm_ss'.txt'").format(new Date(paramInt * 1000L));
	}

	public int[] getImageData(String name, boolean wtf) {
		if (!wtf) {
			Log.w("GenericLauncher","I must return null?");
		}
		System.out.println("Get image data: " + name);
		try {
			InputStream is = getInputStreamForAsset(name);
			if (is == null) {
				Log.e("GenericLauncher", "IMAGE IS NULL!");
				return null;
			}
			Bitmap bmp = BitmapFactory.decodeStream(is);
			int[] retval = new int[(bmp.getWidth() * bmp.getHeight()) + 2];
			retval[0] = bmp.getWidth();
			retval[1] = bmp.getHeight();
			bmp.getPixels(retval, 2, bmp.getWidth(), 0, 0, bmp.getWidth(),
					bmp.getHeight());
			is.close();
			bmp.recycle();

			return retval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/* format: width, height, each integer a pixel */
		/* 0 = white, full transparent */
	}

	public String[] getOptionStrings() {
		Log.e("GenericLauncher", "OptionStrings");
		return new String[] {};
	}

	public void launchUri(String uri) {
		startActivity(new Intent("android.intent.action.View",
			Uri.parse(uri)));
	}

	void pickImage(long paramLong) {
		Log.e("GenericLauncher","pickImage");
	}

	public float getPixelsPerMillimeter() {
		float val = ((float) metrics.densityDpi) / 25.4f;
		System.out.println("Pixels per mm: " + val);
		return val;

	}

	public String getPlatformStringVar(int a) {
		System.out.println("getPlatformStringVar: " + a);
		return "";
	}

	public float getKeyboardHeight() {
		Log.w("GenericLauncher","Bad implemented getKeyboardHeight");
		return (float) getScreenHeight()/2;
	}

	public int getScreenHeight() {
		return metrics.heightPixels;
	}

	public int getScreenWidth() {
		return metrics.widthPixels;
	}

	public int getUserInputStatus() {
		Log.e("GenericLauncher", "User input status");
		return 0;
	}

	public String[] getUserInputString() {
		Log.e("GenericLauncher", "User input string");
		return new String[] {};
	}

	public boolean hasBuyButtonWhenInvalidLicense() {
		return false;
	}

	/** Seems to be called whenever displayDialog is called. Not on UI thread. */
	public void initiateUserInput(int a) {
		System.out.println("initiateUserInput: " + a);
	}

	public boolean isNetworkEnabled(boolean a) {
		System.out.println("Network?:" + a);
		return true;
	}

	public boolean isTouchscreen() {
		return true;
	}

	public void postScreenshotToFacebook(String name, int firstInt,
			int secondInt, int[] thatArray) {
	}

	public void quit() {
		finish();
	}

	public void setIsPowerVR(boolean powerVR) {
		System.out.println("PowerVR: " + powerVR);
	}

	public void tick() {
	}

	public void vibrate(int duration) {
		((Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE))
				.vibrate(duration);
	}

	public boolean supportsNonTouchscreen() {
		boolean xperia = false;
		boolean play = false;
		String[] data = new String[3];
		data[0] = Build.MODEL.toLowerCase(Locale.ENGLISH);
		data[1] = Build.DEVICE.toLowerCase(Locale.ENGLISH);
		data[2] = Build.PRODUCT.toLowerCase(Locale.ENGLISH);
		for (String s : data) {
			if (s.indexOf("xperia") >= 0)
				xperia = true;
			if (s.indexOf("play") >= 0)
				play = true;
		}
		return xperia && play;
	}

	public int getKeyFromKeyCode(int keyCode, int metaState, int deviceId) {
		KeyCharacterMap characterMap = KeyCharacterMap.load(deviceId);
		return characterMap.get(keyCode, metaState);
	}

	public static void saveScreenshot(String name, int firstInt, int secondInt,
			int[] thatArray) {
	}

	public void showKeyboard(final String mystr, final int maxLength,
			final boolean mybool,final boolean isNumber) {
		this.runOnUiThread(new Runnable() {
			public void run() {
				showHiddenTextbox(mystr, maxLength, mybool,isNumber);
			}
		});
	}

	public void hideKeyboard() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				dismissHiddenTextbox();
			}
		});
	}

	public void updateTextboxText(final String text) {
		if (hiddenTextView == null)
			return;
		hiddenTextView.post(new Runnable() {
			public void run() {
				hiddenTextView.setText(text);
			}
		});
	}

	PopupWindow hiddenTextWindow;
	EditText hiddenTextView;
	Boolean hiddenTextDismissAfterOneLine = false;

	private class PopupTextWatcher implements TextWatcher,
			TextView.OnEditorActionListener {
		public void afterTextChanged(Editable e) {
			nativeSetTextboxText(e.toString());

		}

		public void beforeTextChanged(CharSequence c, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence c, int start, int count,
				int after) {
		}

		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (hiddenTextDismissAfterOneLine) {
				hiddenTextWindow.dismiss();
			} else {
				nativeReturnKeyPressed();
			}
			return true;
		}
	}

	public void showHiddenTextbox(String text, int maxLength,
			boolean dismissAfterOneLine,boolean isNumber) {
		int IME_FLAG_NO_FULLSCREEN = 0x02000000;
		if (hiddenTextWindow == null) {
			hiddenTextView = new EditText(this);
			PopupTextWatcher whoWatchesTheWatcher = new PopupTextWatcher();
			hiddenTextView.addTextChangedListener(whoWatchesTheWatcher);
			hiddenTextView.setOnEditorActionListener(whoWatchesTheWatcher);
			hiddenTextView.setSingleLine(true);
			hiddenTextView.setImeOptions(EditorInfo.IME_ACTION_NEXT
					| EditorInfo.IME_FLAG_NO_EXTRACT_UI
					| IME_FLAG_NO_FULLSCREEN);
			hiddenTextView.setInputType(InputType.TYPE_CLASS_TEXT);
			LinearLayout linearLayout = new LinearLayout(this);
			linearLayout.addView(hiddenTextView);
			hiddenTextWindow = new PopupWindow(linearLayout);
			hiddenTextWindow.setWindowLayoutMode(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			hiddenTextWindow.setFocusable(true);
			hiddenTextWindow
					.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
			hiddenTextWindow.setBackgroundDrawable(new ColorDrawable());
			// To get back button handling for free
			hiddenTextWindow.setClippingEnabled(false);
			hiddenTextWindow.setTouchable(false);
			hiddenTextWindow.setOutsideTouchable(true);
			// These flags were taken from a dumpsys window output of Mojang's
			// window
			hiddenTextWindow
					.setOnDismissListener(new PopupWindow.OnDismissListener() {
						public void onDismiss() {
							nativeBackPressed();
						}
					});
		}

		if (isNumber) {
		    hiddenTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		hiddenTextView.setText(text);
		Selection.setSelection((Spannable) hiddenTextView.getText(),
				text.length());
		this.hiddenTextDismissAfterOneLine = dismissAfterOneLine;

		hiddenTextWindow.showAtLocation(this.getWindow().getDecorView(),
				Gravity.LEFT | Gravity.TOP, -10000, 0);
		hiddenTextView.requestFocus();
		showKeyboardView();
	}

	public void addListener(ActivityListener paramActivityListener) {
		this.mActivityListeners.add(paramActivityListener);
	}

	protected void onDestroy() {
		FMOD.close();
		nativeUnregisterThis();
		super.onDestroy();
		System.exit(0);
	}

	public void dismissHiddenTextbox() {
		if (hiddenTextWindow == null)
			return;
		hiddenTextWindow.dismiss();
		hideKeyboardView();
	}

	public void showKeyboardView() {
		Log.i("GenericLauncher", "Show keyboard view");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(getWindow().getDecorView(),
				InputMethodManager.SHOW_FORCED);
	}

	protected void hideKeyboardView() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(this.getWindow().getDecorView()
				.getWindowToken(), 0);
	}

	public int abortWebRequest(int requestId) {
		Log.i("GenericLauncher", "Abort web request: " + requestId);
		return 0;
	}

	public String getRefreshToken() {
		Log.i("GenericLauncher", "Get Refresh token");
		return "";
	}

	public String getSession() {
		Log.i("GenericLauncher", "Get Session");
		return "";
	}

	public String getWebRequestContent(int requestId) {
		Log.e("GenericLauncher", "Get web request content: " + requestId);
		return "";
	}

	public int getWebRequestStatus(int requestId) {
		Log.e("GenericLauncher", "Get web request status: " + requestId);
		return 0;
	}

	public void openLoginWindow() {
		Log.e("GenericLauncher", "Open login window");
	}

	public void setRefreshToken(String token) {
	}

	public void setSession(String session) {
	}

	public void webRequest(int requestId, long timestamp, String url,
			String method, String cookies) {
		Log.e("GenericLauncher", "webRequest");
	}

	// signature change in 0.7.3
	public void webRequest(int requestId, long timestamp, String url,
			String method, String cookies, String extraParam) {
		Log.e("GenericLauncher", "webRequest");
	}

	public String getAccessToken() {
		Log.i("GenericLauncher", "Get access token");
		return "";
	}

	public String getClientId() {
		Log.i("GenericLauncher", "Get client ID");
		return "";
	}

	public String getProfileId() {
		Log.i("GenericLauncher", "Get profile ID");
		return "";
	}

	public String getProfileName() {
		Log.i("GenericLauncher", "Get profile name");
		return "";
	}

	public void statsTrackEvent(String firstEvent, String secondEvent) {
		Log.i("GenericLauncher", "Stats track: " + firstEvent + ":" + secondEvent);
	}

	public void statsUpdateUserData(String firstEvent, String secondEvent) {
		Log.i("GenericLauncher", "Stats update user data: " + firstEvent + ":"
				+ secondEvent);
	}

	public boolean isDemo() {
		Log.i("GenericLauncher", "Is demo");
		return false;
	}

	public void setLoginInformation(String accessToken, String clientId,
			String profileUuid, String profileName) {
		Log.i("GenericLauncher", "setLoginInformation");
	}

	public void clearLoginInformation() {
		Log.i("GenericLauncher", "Clear login info");
	}

  	public String[] getBroadcastAddresses() {
   		return new String[]{};
  	}
  
  	public long getTotalMemory() {
    		ActivityManager localActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
    		MemoryInfo localMemoryInfo = new MemoryInfo();
    		localActivityManager.getMemoryInfo(localMemoryInfo);
    		return localMemoryInfo.availMem;
  	}
  	
  	public void setupTestButton() {
  		TestButton button = new TestButton(this);
  		button.show(getWindow().getDecorView());
  		button.mainButton.setOnClickListener(new View.OnClickListener() {
  			public void onClick(View v) {
  				System.out.println("What does the fox say?");
  			}
  		});
  		
  	}
}
