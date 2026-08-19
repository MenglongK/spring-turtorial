"use client";
import {zodResolver} from "@hookform/resolvers/zod";
import {useState} from "react";
import {useForm} from "react-hook-form";
import {z} from "zod";
import GlassCard from "@/app/admin/_components/GlassCard";
import {buttonClass, Field, Input, Status, Textarea} from "@/app/admin/_components/FormParts";
import {api} from "@/app/admin/_lib/api";

const schema = z.object({
    username: z.string().min(3).max(255),
    email: z.string().email().max(255),
    firstName: z.string().min(1).max(255),
    lastName: z.string().min(1).max(255),
    phoneNumber: z.string().min(9).max(50).optional().or(z.literal("")),
    gender: z.string().max(255).optional(),
    biography: z.string().max(255).optional(),
    password: z.string().min(1).max(255),
    confirmedPassword: z.string().min(1).max(255)
}).refine((value) => value.password === value.confirmedPassword, {
    path: ["confirmedPassword"],
    message: "Passwords do not match"
});
type RegisterForm = z.infer<typeof schema>;
export default function AdminAuthPage() {
    const [error, setError] = useState<string>();
    const [success, setSuccess] = useState<string>();
    const {
        register,
        handleSubmit,
        reset,
        formState: {errors, isSubmitting}
    } = useForm<RegisterForm>({resolver: zodResolver(schema)});
    const submit = async (data: RegisterForm) => {
        try {
            await api.register(data);
            setSuccess("User registered successfully.");
            reset();
        } catch (e) {
            setError(e instanceof Error ? e.message : "Registration failed.");
        }
    };
    return <GlassCard title="Register a user"
                      subtitle="POST /auth/register — sign-in is handled by Keycloak/BFF, not this API.">
        <form onSubmit={handleSubmit(submit)} className="grid gap-3 md:grid-cols-2"><Field label="Username"
                                                                                           error={errors.username?.message}><Input {...register("username")} /></Field><Field
            label="Email" error={errors.email?.message}><Input type="email" {...register("email")} /></Field><Field
            label="First name" error={errors.firstName?.message}><Input {...register("firstName")} /></Field><Field
            label="Last name" error={errors.lastName?.message}><Input {...register("lastName")} /></Field><Field
            label="Phone number"
            error={errors.phoneNumber?.message}><Input {...register("phoneNumber")} /></Field><Field
            label="Gender"><Input {...register("gender")} /></Field><Field label="Password"
                                                                           error={errors.password?.message}><Input
            type="password" {...register("password")} /></Field><Field label="Confirm password"
                                                                       error={errors.confirmedPassword?.message}><Input
            type="password" {...register("confirmedPassword")} /></Field>
            <div className="md:col-span-2"><Field label="Biography"><Textarea
                rows={3} {...register("biography")} /></Field></div>
            <div className="md:col-span-2"><Status error={error} success={success}/></div>
            <button disabled={isSubmitting}
                    className={`${buttonClass} md:col-span-2`}>{isSubmitting ? "Registering…" : "Register user"}</button>
        </form>
    </GlassCard>;
}
