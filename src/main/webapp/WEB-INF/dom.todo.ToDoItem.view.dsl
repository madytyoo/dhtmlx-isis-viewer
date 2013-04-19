1C [
    a[
        text : "Title"
        @Top[ "<b>Toolbar</b>" , | , completed(), | , notYetCompleted() , | , duplicate() ]
        3L[
            a[
                /* text : "General" */
                width: 400
                [
                        "General" [
                        _message
                        description
                        category
                        complete
                        dueBy
                        notes
                        ]
                        "Attachment" [
                            attachment
                        ]
                        [edit(),ok(),cancel()]
                ]
            ]
            b[
                text : "<b>Dependencies</b>"
                @Top[ ||, add() , | ,  remove() ]
                dependencies < 'description,category,dueby,complete,attachment' >
            ]
            c[
                text : "<b>Similar Items</b>"
                similarItems < 'title,description,category,dueby,complete' >
            ]
        ]
    ]
]
