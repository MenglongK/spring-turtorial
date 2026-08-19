import Link from "next/link";

const navItems = [
  { href: "/admin", label: "Overview" },
  { href: "/admin/product", label: "Products" },
  { href: "/admin/category", label: "Categories" },
  { href: "/admin/order", label: "Create order" },
  { href: "/admin/fileupload", label: "Media library" },
  { href: "/admin/profile", label: "My profile" },
  { href: "/admin/auth", label: "Register user" },
];

export default function AdminLayout({ children }: LayoutProps<"/admin">) {
  return (
    <div className="relative min-h-screen overflow-hidden bg-slate-950 text-slate-100">
      <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(circle_at_10%_20%,#3b82f655,transparent_35%),radial-gradient(circle_at_90%_10%,#a855f755,transparent_35%),radial-gradient(circle_at_50%_100%,#06b6d455,transparent_35%)]" />

      <div className="relative mx-auto grid min-h-screen w-full max-w-7xl grid-cols-1 gap-6 p-4 md:grid-cols-[260px_1fr] md:p-8">
        <aside className="rounded-3xl border border-white/15 bg-white/[0.07] p-5 shadow-2xl shadow-slate-950/30 backdrop-blur-xl">
          <div className="flex size-10 items-center justify-center rounded-2xl bg-gradient-to-br from-cyan-300 to-indigo-500 text-lg font-black text-slate-950">G</div>
          <h2 className="mt-4 text-lg font-bold text-white">GlassMart</h2>
          <p className="mt-1 text-xs text-slate-200/80">Internal commerce console</p>
          <nav className="mt-6 space-y-2">
            {navItems.map((item) => (
              <a
                key={item.href}
                href={item.href}
                className="block rounded-xl border border-transparent px-4 py-2.5 text-sm text-slate-200 transition hover:border-white/15 hover:bg-white/10 hover:text-white"
              >
                {item.label}
              </a>
            ))}
          </nav>
        </aside>

        <div className="rounded-3xl border border-white/15 bg-white/[0.07] p-5 shadow-2xl shadow-slate-950/30 backdrop-blur-xl md:p-8">
          <header className="mb-6 flex flex-wrap items-center justify-between gap-3 rounded-2xl border border-white/15 bg-slate-950/25 px-4 py-3">
            <div>
              <h1 className="text-xl font-semibold text-white">Admin Dashboard</h1>
              <p className="text-xs text-slate-200/80">Manage your store operations</p>
            </div>
            <Link
              href="/"
              className="rounded-lg border border-white/30 bg-white/10 px-3 py-2 text-xs font-semibold text-white transition hover:bg-white/20"
            >
              Back to Landing
            </Link>
          </header>
          {children}
        </div>
      </div>
    </div>
  );
}
