export const TRANSLATIONS_EN = {
    PageName: "Getaway",

    Categories: {
        Aventura: "Adventure",
        Gastronomia: "Gastronomy",
        Hoteleria: "Hotels",
        Relax: "Relax",
        Vida_nocturna: "Nightlife",
        Historico: "Historic",
    },

    Carousel: {
        experienceEmpty: "This category doesn't have enough experiences yet"
    },

    Navbar: {
        createAccount: "Sign up",
        createExperience: "Create experience",
        email: "Email",
        search: "Search",
        forgotPassword: "Forgot yor password?",
        login: "Sign in",
        loginTitle: "Sign in to Getaway",
        loginDescription: "New experiences every day",
        newUser: "New to Getaway?",
        password: "Password",
        confirmPassword: "Confirm password",
        profile: "My profile",
        experiences: "My experiences",
        favourites: "My favourites",
        reviews: "My reviews",
        rememberMe: "Remember me",
        logout: "Log out",
        resetPasswordTitle: "Enter your email and get a link to recover your password",
        resetPasswordButton: "Send",
        createAccountPopUp: "Create your account",
        createAccountDescription: "Add your info in order to start creating your experiences and reviews",
        max: "(Max {{num}})",
        name: "Name",
        surname: "Surname",
        createButton: "Create account",
        emailPlaceholder: "jhondoe@example.com",
        namePlaceholder: "Jhon",
        surnamePlaceholder: "Doe",
        passwordPlaceholder: "Between 8 and 25 characters"
    },

    Filters: {
        title: "Filters",
        city: {
            field: "City",
            placeholder: "Where?",
        },
        price: {
            title: "Price",
            min: "0",
        },
        scoreAssign: "Score",
        btn: {
            submit: "Search",
            clear: "Clean filters",
        },
    },

    Experience: {
        name: "Name",
        category: "Category",
        price: {
            name: "Price",
            null: "Price not listed",
            free: "Free",
            exist: "${{price}}",
        },
        information: "Description",
        mail: {
            field: "Email",
            placeholder: "juanmartinez@example.com",
        },
        url: {
            field: "Url",
            placeholder: "https://google.com",
        },
        country: "Country",
        city: "City",
        address: "Address",
        image: "Image",
        placeholder: "Write in order to search",
        reviews: "Reviews {{reviewCount}}",
        notVisible: "This experience is not currently visible",
    },

    ExperienceDetail: {
        imageDefault: "This image does not belong to the experience",
        price: {
            null: "Price not listed",
            free: "Free",
            exist: "${{price}}",
        },
        description: "Description",
        noData: "Information not provided",
        url: "Official site",
        email: "Email",
        review: "Reviews",
        writeReview: "Write review",
        notVisible: "This experience is not currently visible",
        noReviews: "This experience has no reviews yet. Be the first to write one!"
    },

    Review: {
        title: "Title",
        description: "Description",
        score: "Score",
    },

    ExperienceForm: {
        title: "Create your experience",
        error: {
            name: {
                pattern: "aaaa",
                isRequired: "aaa",
                max: "aaaa",
            },
            category: {
                isRequired: "aaaa",
            },
            description: {
                pattern: "aaaa",
                isRequired: "aaaa",
                max: "aaaa",
            },
            mail: {
                pattern: "aaaa",
                isRequired: "aaaa",
                max: "aaaa",
            },
            url: {
                pattern: "aaaa",
                max: "aaaa",
            },
            country: {
                isRequired: "aaaa",
            },
            city: {
                isRequired: "aaaa",
            },
            address: {
                pattern: "aaaa",
                isRequired: "aaaa",
                max: "aaaa",
            },
        }
    },

    User: {
        profile: {
            description: "My profile",
            name: "Name: {{userName}}",
            surname: "Surname: {{userSurname}}",
            email: "Email: {{userEmail}}",
            editBtn: "Edit profile",
            verifyAccountBtn: "Verify your account",
        },
        experiences: {
            title: "My experiences",
            category: "Category",
            score: "Score",
            price: "Price",
            views: "Views",
            actions: "Actions",
            reviewsCount: "Reseñas {{count}}"
        },
        noExperiences: "You haven't created any experience yet",
        experiencesTitle: "My experiences",
        noFavs: "You haven't added any favourite experiences yet",
        favsTitle: "My favourites",
        noReviews: "You haven't written any reviews yet",
        reviewsTitle: "My reviews",
    },

    EmptyResult: "It seems there are no experiences matching your search",

    Button: {
        cancel: "Cancel",
        create: "Save",
    },

    Input: {
        optional: "(Optional)",
        maxValue: "(Max {{value}})",
    },

    CreateReview: {
        title: "Create review for {{experienceName}}",
        error: {
            title: {
                pattern: "aaaa",
                isRequired: "aaa",
                max: "aaaa",
            },
            description: {
                pattern: "aaaa",
                isRequired: "aaaa",
                max: "aaaa",
            },
            score: {
                pattern: "aaaa",
                isRequired: "aaaa",
            },
        }
    },

    Copyright: "Getaway Copyright © {{year}} - All rights reserved",

    404: {
        title: "Error 404",
        description: "It seems the page you're looking for doesn't exist.",
    },

    Error: {
        whoops: "Whoops!",
        backBtn: "Home",
        title: "Error {{errorCode}}",
    },

    Pagination: {
        message: "Page {{currentPage}} of {{maxPage}}",
        alt: {
            nextPage: "Next page",
            beforePage: "Before page",
        },
    },

    Order: {
        title: "Order by: ",
        OrderByRankAsc: "Ascendant score",
        OrderByRankDesc: "Descendant score",
        OrderByAZ: "A-Z",
        OrderByZA: "Z-A",
        OrderByLowPrice: "Lower price",
        OrderByHighPrice: "Higher price",
        OrderByViewAsc: "Less views",
        OrderByViewDesc: "More views",
    },

    Landing: {
        user: {
            viewed: "Last viewed",
            recommendedByFavs: "Based on your favourites",
            recommendedByReviews: "Based on your reviews",
        },
        anonymous: {
            aventura: "Top ranked of adventur",
            gastronomia: "Top ranked of gastronomy",
            hoteleria: "Top ranked of hotels",
            relax: "Top ranked of relax",
            vida_nocturna: "Top ranked of nightlife",
            historico: "Top ranked of historic",
        },
    },

    CreateAccount: {
        error:{
            isRequired: "This field is required",
            pattern:"Error",
            password:"Confirm password should match password",
        }
    },

    Experiences: {
        search: {
            search:"Searching ",
            category: "in ",
            name: ": \" {{name}} \""
        }
    },
};