"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import GlassCard from "@/app/admin/_components/GlassCard";
import { Field, Input, Status, Textarea, buttonClass } from "@/app/admin/_components/FormParts";
import { api } from "@/app/admin/_lib/api";

const schema = z.object({ firstName: z.string().max(255).optional(), lastName: z.string().max(255).optional(), gender: z.string().max(255).optional(), biography: z.string().max(255).optional(), profilePicture: z.string().url().or(z.literal("")).optional(), jobTitle: z.string().max(255).optional(), salary: z.number().min(0).optional(), phoneNumber: z.string().max(255).optional(), githubLink: z.string().url().or(z.literal("")).optional(), facebookLink: z.string().url().or(z.literal("")).optional() });
type ProfileForm = z.infer<typeof schema>;

export default function AdminProfilePage() {
  const [identity, setIdentity] = useState<{ email?: string; userId?: string }>({}); const [error, setError] = useState<string>(); const [success, setSuccess] = useState<string>();
  const { register, handleSubmit, reset, formState: { errors, isSubmitting } } = useForm<ProfileForm>({ resolver: zodResolver(schema) });
  useEffect(() => { api.getProfile().then((profile) => { setIdentity(profile); reset({ firstName: profile.firstName ?? "", lastName: profile.lastName ?? "", gender: profile.gender ?? "", biography: profile.biography ?? "", profilePicture: profile.profilePicture ?? "", jobTitle: profile.jobTitle ?? "", salary: profile.salary ?? undefined, phoneNumber: profile.phoneNumber ?? "", githubLink: profile.githubLink ?? "", facebookLink: profile.facebookLink ?? "" }); }).catch((caught: Error) => setError(caught.message)); }, [reset]);
  const submit = async (data: ProfileForm) => { try { await api.patchProfile(data); setSuccess("Profile updated."); } catch (caught) { setError(caught instanceof Error ? caught.message : "Could not update profile."); } };
  return <GlassCard title="My profile" subtitle="GET and PATCH /user-profiles/me"><form onSubmit={handleSubmit(submit)} className="grid gap-4"><div className="rounded-2xl border border-white/15 bg-slate-950/25 px-4 py-3 text-sm text-slate-300"><p>{identity.email || "Loading signed-in user…"}</p>{identity.userId && <p className="mt-1 text-xs text-slate-500">{identity.userId}</p>}</div><div className="grid gap-3 sm:grid-cols-2"><Field label="First name" error={errors.firstName?.message}><Input {...register("firstName")} /></Field><Field label="Last name" error={errors.lastName?.message}><Input {...register("lastName")} /></Field><Field label="Job title" error={errors.jobTitle?.message}><Input {...register("jobTitle")} /></Field><Field label="Gender" error={errors.gender?.message}><Input {...register("gender")} /></Field><Field label="Phone number" error={errors.phoneNumber?.message}><Input {...register("phoneNumber")} /></Field><Field label="Salary" error={errors.salary?.message}><Input type="number" step="0.01" {...register("salary", { setValueAs: (value) => value === "" ? undefined : Number(value) })} /></Field><Field label="Profile picture URL" error={errors.profilePicture?.message}><Input {...register("profilePicture")} /></Field><Field label="GitHub URL" error={errors.githubLink?.message}><Input {...register("githubLink")} /></Field><Field label="Facebook URL" error={errors.facebookLink?.message}><Input {...register("facebookLink")} /></Field></div><Field label="Biography" error={errors.biography?.message}><Textarea rows={4} {...register("biography")} /></Field><Status error={error} success={success} /><button disabled={isSubmitting} className={`${buttonClass} w-fit`}>{isSubmitting ? "Updating…" : "Save profile"}</button></form></GlassCard>;
}
