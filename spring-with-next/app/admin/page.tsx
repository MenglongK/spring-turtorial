import Link from "next/link";

const modules = [
  { title: "Products", detail: "Browse, add, update and remove catalog items.", href: "/admin/product", method: "GET · POST · PUT · PATCH · DELETE" },
  { title: "Categories", detail: "Maintain the taxonomy used when products are created.", href: "/admin/category", method: "GET · POST" },
  { title: "Media library", detail: "Browse uploaded media and upload one or many files.", href: "/admin/fileupload", method: "GET · POST" },
  { title: "My profile", detail: "View and update the authenticated operator profile.", href: "/admin/profile", method: "GET · PATCH" },
  { title: "Orders", detail: "Create a new order with one or more product lines.", href: "/admin/order", method: "POST" },
  { title: "User registration", detail: "Provision a new user through the auth API.", href: "/admin/auth", method: "POST" },
];

export default function AdminHomePage() {
  return <section className="space-y-6"><div className="rounded-3xl border border-cyan-200/15 bg-gradient-to-br from-cyan-300/10 via-indigo-400/10 to-fuchsia-400/10 p-6"><p className="text-xs font-semibold uppercase tracking-[0.2em] text-cyan-200">Operations workspace</p><h2 className="mt-3 max-w-xl text-3xl font-semibold tracking-tight text-white">Every screen is shaped by the APIs you have today.</h2><p className="mt-3 max-w-2xl text-sm leading-6 text-slate-200/80">Use the BFF-hosted dashboard so requests flow through <code className="rounded bg-slate-950/30 px-1.5 py-0.5 text-cyan-100">/ecommerce-api</code> with the current session. Set <code className="rounded bg-slate-950/30 px-1.5 py-0.5 text-cyan-100">NEXT_PUBLIC_BFF_API_URL</code> only when using a different gateway path.</p></div><div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">{modules.map((module) => <Link key={module.href} href={module.href} className="group rounded-2xl border border-white/15 bg-white/[0.06] p-5 shadow-xl shadow-slate-950/20 transition hover:-translate-y-0.5 hover:border-cyan-200/35 hover:bg-white/[0.1]"><p className="text-xs font-medium text-cyan-200">{module.method}</p><h3 className="mt-3 text-lg font-semibold text-white">{module.title}</h3><p className="mt-2 text-sm leading-6 text-slate-300">{module.detail}</p><span className="mt-5 inline-block text-sm font-medium text-white group-hover:text-cyan-200">Open module →</span></Link>)}</div></section>;
}
