"use client";

import {InputHTMLAttributes, ReactNode, TextareaHTMLAttributes} from "react";

const controlClass = "w-full rounded-xl border border-white/15 bg-slate-950/35 px-3 py-2.5 text-sm text-white outline-none transition placeholder:text-slate-400 focus:border-cyan-300/60 focus:ring-2 focus:ring-cyan-300/15";

export function Field({label, error, children}: { label: string; error?: string; children: ReactNode }) {
    return <label className="grid gap-1.5 text-xs font-medium text-slate-200">{label}{children}{error &&
        <span className="text-rose-300">{error}</span>}</label>;
}

export function Input(props: InputHTMLAttributes<HTMLInputElement>) {
    return <input {...props} className={`${controlClass} ${props.className ?? ""}`}/>;
}

export function Textarea(props: TextareaHTMLAttributes<HTMLTextAreaElement>) {
    return <textarea {...props} className={`${controlClass} resize-y ${props.className ?? ""}`}/>;
}

export function Select({children, className, ...props}: React.SelectHTMLAttributes<HTMLSelectElement>) {
    return <select {...props} className={`${controlClass} ${className ?? ""}`}>{children}</select>;
}

export const buttonClass = "inline-flex items-center justify-center rounded-xl bg-gradient-to-r from-cyan-400 to-indigo-500 px-4 py-2.5 text-sm font-semibold text-slate-950 shadow-lg shadow-cyan-950/40 transition hover:brightness-110 disabled:cursor-not-allowed disabled:opacity-60";

export function Status({error, success}: { error?: string; success?: string }) {
    return error ?
        <p className="rounded-lg border border-rose-300/20 bg-rose-400/10 px-3 py-2 text-xs text-rose-200">{error}</p> : success ?
            <p className="rounded-lg border border-emerald-300/20 bg-emerald-400/10 px-3 py-2 text-xs text-emerald-200">{success}</p> : null;
}
