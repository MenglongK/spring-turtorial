"use client";

import {zodResolver} from "@hookform/resolvers/zod";
import {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {z} from "zod";
import GlassCard from "@/app/admin/_components/GlassCard";
import {buttonClass, Field, Input, Status, Textarea} from "@/app/admin/_components/FormParts";
import {api, Category} from "@/app/admin/_lib/api";

const schema = z.object({
    name: z.string().min(2).max(50),
    description: z.string().optional(),
    icon: z.string().optional()
});
type CategoryForm = z.infer<typeof schema>;

export default function AdminCategoryPage() {
    const [categories, setCategories] = useState<Category[]>([]);
    const [editing, setEditing] = useState<Category>();
    const [note, setNote] = useState<string>();
    const [error, setError] = useState<string>();
    const {
        register,
        handleSubmit,
        reset,
        formState: {errors, isSubmitting}
    } = useForm<CategoryForm>({resolver: zodResolver(schema)});
    const load = () => api.getCategories().then((data) => setCategories(data.content)).catch((caught: Error) => setError(caught.message));
    useEffect(() => {
        void load();
    }, []);
    const cancel = () => {
        setEditing(undefined);
        reset({name: "", description: "", icon: ""});
    };
    const edit = (category: Category) => {
        setEditing(category);
        reset({name: category.name, description: category.description ?? "", icon: category.icon ?? ""});
    };
    const submit = async (values: CategoryForm) => {
        setError(undefined);
        try {
            if (editing) {
                await api.updateCategory(editing.id, values);
                setNote("Category updated.");
            } else {
                await api.createCategory(values);
                setNote("Category created.");
            }
            cancel();
            await load();
        } catch (caught) {
            setError(caught instanceof Error ? caught.message : "Could not save category.");
        }
    };
    const remove = async (category: Category) => {
        if (!window.confirm(`Delete ${category.name}?`)) return;
        try {
            await api.deleteCategory(category.id);
            setCategories((current) => current.filter((item) => item.id !== category.id));
        } catch (caught) {
            setError(caught instanceof Error ? caught.message : "Could not delete category.");
        }
    };
    return <GlassCard title="Category management" subtitle="Create, edit and delete product taxonomy">
        <div className="grid gap-6 lg:grid-cols-[1fr_340px]">
            <div className="grid gap-3 sm:grid-cols-2">{categories.map((category) => <article key={category.id}
                                                                                              className="rounded-2xl border border-white/15 bg-white/[0.05] p-4">
                <p className="text-lg">{category.icon || "◌"}</p><h3
                className="mt-2 font-semibold text-white">{category.name}</h3><p
                className="mt-1 min-h-10 text-sm text-slate-300">{category.description || "No description"}</p>
                <div className="mt-4 flex gap-4">
                    <button onClick={() => edit(category)} className="text-xs text-cyan-200">Edit</button>
                    <button onClick={() => void remove(category)} className="text-xs text-rose-300">Delete</button>
                </div>
            </article>)}{!categories.length &&
                <p className="text-sm text-slate-400">No categories returned by the API.</p>}</div>
            <form onSubmit={handleSubmit(submit)}
                  className="grid content-start gap-3 rounded-2xl border border-white/15 bg-slate-950/25 p-4">
                <div className="flex items-center justify-between"><h3
                    className="text-sm font-semibold text-white">{editing ? `Edit ${editing.name}` : "New category"}</h3>{editing &&
                    <button type="button" onClick={cancel} className="text-xs text-cyan-200">Cancel</button>}</div>
                <Field label="Name" error={errors.name?.message}><Input {...register("name")} /></Field><Field
                label="Icon"><Input placeholder="e.g. ✦" {...register("icon")} /></Field><Field
                label="Description"><Textarea rows={4} {...register("description")} /></Field><Status error={error}
                                                                                                      success={note}/>
                <button disabled={isSubmitting}
                        className={buttonClass}>{isSubmitting ? "Saving…" : editing ? "Update category" : "Create category"}</button>
            </form>
        </div>
    </GlassCard>;
}
