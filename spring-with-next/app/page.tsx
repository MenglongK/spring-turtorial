import CategoryCardList from "@/app/CategoryCardList";

export default function Home() {

    return (
        <div className="flex flex-col flex-1 items-center justify-center bg-zinc-50 font-sans dark:bg-black">
            <h1>Welcome to my page</h1>
            <CategoryCardList/>
        </div>
    );
}
