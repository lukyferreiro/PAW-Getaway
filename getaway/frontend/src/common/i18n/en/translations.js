export const TRANSLATIONS_EN = {

    PageName: "Getaway",

    PageTitles: {
        changePassword: "Change Password",
        createAccount: "Sign Up",
        custom404: "404",
        error: "Error",
        experienceDetails: "{{experienceName}}",
        experienceForm: {
            create: "Create Experience",
            edit: "Edit Experience",
        },
        experiences: "Experiences",
        login: "Sign In",
        reviewForm: {
            create: "Create Review",
            edit: "Edit Review",
        },
        userEditProfile: "Edit Profile",
        userExperiences: "My Experiences",
        userFavourites: "My Favourites",
        userProfile: "My Profile",
        userReviews: "My Reviews",
    },

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
        logout: "Log out",
        resetPasswordTitle: "Enter your email and get a link to recover your password",
        resetPasswordButton: "Send",
        createAccountPopUp: "Create your account",
        createAccountDescription: "Add your info in order to start creating your experiences and reviews",
        max: "(Max {{num}})",
        name: "Name",
        surname: "Surname",
        createButton: "Create account",
        emailPlaceholder: "johndoe@example.com",
        namePlaceholder: "John",
        surnamePlaceholder: "Doe",
        passwordPlaceholder: "Between 8 and 25 characters",
        editProfilePopUp: "Edit your profile",
        editPasswordPopUp: "Change your password",
        editPassword: "New password",
        confirmEditPassword: "Confirm new password",
        changePassword: "Change password",
        editProfile: "Edit profile",
        error: "Less than 50 characters"
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
        scoreAssign: "Minimum score",
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
            exist: "$ {{price}}",
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
        imgTitle: "Select an image for the experinece",
        toast: {
            imageSuccess: "Experience image updated successfully!",
            imageInvalidFormat: "Image format is invalid",
            imageError: "A server error occurred when updating the image",
            favSuccess: "'{{experienceName}}' was added to your favourites",
            noFavSuccess: "'{{experienceName}}' was removed from your favourites",
            favError: "Server error adding '{{experienceName}}'",
            noFavError: "Server error removing '{{experienceName}}'",
            favNotSigned: "Sign in to fav an experience",
            visibilitySuccess: "'{{experienceName}}' is now visible to all users",
            noVisibilitySuccess: "'{{experienceName}}' was hidden",
            visibilityError: "Server error changing '{{experienceName}}' visibility",
            deleteSuccess: "'{{experienceName}}' was deleted successfully",
            deleteError: "Server error deleting '{{experienceName}}'",
        },
    },

    ExperienceDetail: {
        imageDefault: "This image does not belong to the experience",
        price: {
            null: "Price not listed",
            free: "Free",
            exist: "$ {{price}}",
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

    ExperienceForm: {
        title: "Create your experience",
        edit: "Edit your experience",
        error: {
            name: {
                pattern: "Submitted name format is not valid",
                isRequired: "This field can not be empty",
                length: "Experience name must be 3-50 characters long",
            },
            category: {
                isRequired: "This field can not be empty",
            },
            price: {
                max: "Price must be under $9999999",
            },
            description: {
                pattern: "Submitted description format is not valid",
                isRequired: "This field can not be empty",
                length: "Description must be under 500 characters long",
            },
            mail: {
                pattern: "Submitted mail format is not valid",
                isRequired: "This field can not be empty",
                length: "The email must be under 255 characters long",
            },
            url: {
                pattern: "Submitted url format is not valid",
                length: "The URL must be under 500 characters long",
            },
            country: {
                isRequired: "This field can not be empty",
            },
            city: {
                isRequired: "This field can not be empty",
            },
            address: {
                pattern: "Submitted address format is not valid",
                isRequired: "This field can not be empty",
                length: "Address must be 5-100 characters long",
            },
        },
        toast: {
            forbidden: {
                noUser: "Sign in to create an experience",
                notVerified: "Verify your account to create an experience",
                notAllowed: "You can not modify this experience",
            },
            createSuccess: "'{{experienceName}}' created successfully!",
            createError: "Server error creating experience '{{experienceName}}'",
            updateSuccess: "'{{experienceName}}' updated successfully!",
            updateError: "Server error updating experience '{{experienceName}}'",
        },
    },

    User: {
        profile: {
            description: "My profile",
            name: "Name: {{userName}}",
            surname: "Surname: {{userSurname}}",
            email: "Email: {{userEmail}}",
            editBtn: "Edit profile",
            verifyAccountBtn: "Verify your account",
            photo: "Change profile image"
        },
        experiences: {
            title: "My experiences",
            category: "Category",
            score: "Score",
            price: "Price",
            views: "Views",
            actions: "Actions",
            reviewsCount: "Reviews ({{count}})",
            deleteTitle: "Delete experience",
            confirmDelete: "Are you sure you want to delete experience: {{experienceName}}?"
        },
        noExperiences: "You haven't created any experience yet",
        experiencesTitle: "My experiences",
        noFavs: "You haven't added any favourite experiences yet",
        favsTitle: "My favourites",
        noReviews: "You haven't written any reviews yet",
        reviewsTitle: "My reviews",
        imgTitle: "Select a profile image",
        toast: {
            imageSuccess: "Profile image updated successfully!",
            imageError: "Server error when updating profile image",
            imageInvalidFormat: "Image format is invalid",
            passwordResetEmailSuccess: "Email sent successfully",
            passwordResetEmailError: "Server error when sending email",
            notSigned: "Sign in to access your profile information",
            alreadySigned: "You are already sign in",
            sessionExpired: "Your session has expired",
            verify: {
                success: "Your account was verified successfully",
                error: "Invalid token",
                alreadyVerified: "Your account is already verified",
            },
            sendVerify: "Email sent to verify your account",
            resendVerify: {
                success: "Email sent successfully",
                error: "Server error when sending email",
            },
            editProfile: {
                success:"Profile information updated successfully",
                error: "Server error when updating profile information",
                forbidden: "Verify your account to edit your profile",
            },
            experiences: {
                forbidden: "Create at least one experience to access 'My Experiences'",
            },
            reviews: {
                forbidden: "Verify your account to access 'My Reviews'",
            },
        },
    },

    EmptyResult: "It seems there are no experiences matching your search",

    Button: {
        cancel: "Cancel",
        create: "Save",
        confirm: "Confirm"
    },

    Input: {
        optional: "(Optional)",
        maxValue: "(Max {{value}})",
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
            aventura: "Top ranked of adventure",
            gastronomia: "Top ranked of gastronomy",
            hoteleria: "Top ranked of hotels",
            relax: "Top ranked of relax",
            vida_nocturna: "Top ranked of nightlife",
            historico: "Top ranked of historic",
        },
    },

    CreateAccount: {
        error: {
            email: {
                isRequired: "This field is required",
                length: "The email must be under 255 characters long",
                pattern: "Submitted email format is not valid",
            },
            name: {
                isRequired: "This field is required",
                length: "Name must be under 50 characters long",
                pattern: "Submitted name format is not valid",
            },
            surname: {
                isRequired: "This field is required",
                length: "Surname must be under 50 characters long",
                pattern: "Submitted surname format is not valid",
            },
            password: {
                isRequired: "This field is required",
                length: "Password must be 8-25 characters long",
                pattern: "Submitted password format is not valid",
            },
            passwordsMustMatch: "Confirm password must match password",
        },
        toast: {
            error: "Server error when signing up",
        }
    },

    Login: {
        toast: {
            success: "Welcome {{name}} {{surname}}!",
            error: "Server error when signing in",
            verifySent: "Verification email sent",
        },
        invalidCredentials: "Incorrect Email or password"
    },

    ChangePassword: {
        title: "Enter your new password",
        invalidEmail: "Submitted email is not valid",
        toast: {
            forbidden: "You can not change your password if you are signed in",
            missPasswordToken: "Missing token",
            success: "Password changed successfully!",
            error: "Invalid token",
        },
    },

    Experiences: {
        search: {
            search: "Searching ",
            category: "in ",
            name: " \"{{name}}\""
        }
    },

    Review: {
        title: "Title",
        description: "Description",
        score: "Score",
        deleteModal: {
            title: "Delete review",
            confirmDelete: "Are you sure you want to delete review: {{reviewTitle}}?",
        },
        toast: {
            deleteSuccess: "'{{reviewTitle}}' deleted successfully",
            deleteError: "Server error deleting review '{{reviewTitle}}'",
        },
    },

    ReviewForm: {
        title: "Write a review for {{experienceName}}",
        editTitle: "Edit the review for {{experienceName}}",
        error: {
            title: {
                pattern: "Submitted title format is not valid",
                isRequired: "This field can not be empty",
                length: "Title must be 3-50 characters long",
            },
            description: {
                pattern: "Submitted description format is not valid",
                isRequired: "This field can not be empty",
                length: "Description must be 3-255 characters long",
            },
            score: {
                isRequired: "This field can not be empty",
            },
        },
        toast: {
            forbidden: {
                noUser: "Sign in to create reviews",
                notVerified: "Verify your account to create reviews",
                notAllowed: "You can not modify this review",
            },
            createSuccess: "'{{reviewTitle}}' created successfully!",
            createError: "Server error creating review '{{reviewTitle}}'",
            updateSuccess: "'{{reviewTitle}}' updated successfully!",
            updateError: "Server error updating review '{{reviewTitle}}'",
        },
    },

    Image: {
        error: {
            isRequired: "This field can not be empty",
            size: "The image is too big",
        },
    },

    AriaLabel: {
        fav: "Save in favourites",
        closeForm: "Delete search",
        leftArrow: "Previous experience",
        rightArrow: "Next experience",
        cancel: "Cancel",
        confirm: "Confirm",
        save: "Save",
        order: "Order",
        send: "Send",
        search: "Search",
        createExperience: "Create experience",
        createReview: "Create review",
        login: "Sign in",
        signOut: "Sign out",
        profileInfo: "My account",
        createAccount: "Create account",
        home: "Home",
        writeReview: "Write review",
        cleanFilter: "Clean filters",
        editImage: "Edit image",
        editProfile: "Edit profile",
        verifyAccount: "Verify account",
        visibility: "Visibility",
        editExperience: "Edit experience",
        deleteExperience: "Eliminar experience",
        editReview: "Edit review",
        deleteReview: "Eliminar review",
        showPassword: "Show password",
    },
};