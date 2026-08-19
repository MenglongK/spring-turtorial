import {ReactNode} from "react";

type GlassCardProps = {
    title: string;
    subtitle?: string;
    children: ReactNode;
};

export default function GlassCard({title, subtitle, children}: GlassCardProps) {
    return (
        <article
            className="rounded-3xl border border-white/15 bg-white/[0.07] p-5 shadow-2xl shadow-slate-950/25 backdrop-blur-xl">
            <h2 className="text-base font-semibold text-white">{title}</h2>
            {subtitle ? <p className="mt-1 text-xs text-slate-200/80">{subtitle}</p> : null}
            <div className="mt-4">{children}</div>
        </article>
    );
}
