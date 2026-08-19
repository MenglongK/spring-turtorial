"use client"

import {useEffect, useState} from "react";

type CategoryType = {
    id: number,
    name: string,
    description: string,
    icon: string
}

export default function CategoryCardList() {

    const [categories, setCategories] = useState<CategoryType[]>([])

    useEffect(() => {
        function fetchCategories() {
            fetch("/ecommerce-api/api/v1/categories")
                .then(res => res.json())
                .then(json => {
                    console.log("Categories:", json)
                    setCategories(json.content)
                })
        }
        fetchCategories()
    }, [])

    return (
        <>
            {
                categories && categories.map(category => (
                    <span key={category.id} className="rounded-full bg-purple-100 px-2.5 py-0.5 text-sm whitespace-nowrap text-purple-700">
                      {category.name}
                    </span>
                ))
            }
        </>
    )
}