package fb;

import android.os.AsyncTask;

import fb.C2332f.C2330a;

class C2333g extends AsyncTask<Integer, Integer, C2330a> {
    final /* synthetic */ C2328d f6256a;
    private C2335h f6257b;
    private C2329e f6258c;

    public C2333g(C2328d c2328d, C2335h c2335h, C2329e c2329e) {
        this.f6256a = c2328d;
        this.f6257b = c2335h;
        this.f6258c = c2329e;
    }

    protected C2330a m10316a(Integer... numArr) {
        return this.f6256a.m10312a(this.f6257b);
    }

    protected void m10317a(C2330a c2330a) {
        if (this.f6258c != null) {
            this.f6258c.m10315a(c2330a);
        }
    }

   /* protected /* synthetic */ /*Object doInBackground(Object... objArr) {
        return m10316a((Integer[]) objArr);
    }

    protected /* synthetic */ /*void onPostExecute(Object obj) {
        m10317a((C2330a) obj);
    }*/

    @Override
    protected C2330a doInBackground(Integer... params) {
        return null;
    }

    protected void onPreExecute() {
        if (this.f6258c != null) {
            this.f6258c.m10314a();
        }
    }
}
