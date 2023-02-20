export const TRANSLATIONS_ES = {

    PageName: "Getaway",

    Categories: {
        Aventura: "Aventura",
        Gastronomia: "Gastronom\u00EDa",
        Hoteleria: "Hoteler\u00EDa",
        Relax: "Relax",
        Vida_nocturna: "Vida nocturna",
        Historico: "Hist\u00F3rico",
    },

    Carousel: {
        experienceEmpty: "Esta categor\u00EDa aun no tiene suficientes experiencias cargadas"
    },

    Navbar: {
        createAccount: "Crea una cuenta",
        createExperience: "Crear experiencia",
        email: "Email",
        search: "Buscar",
        forgotPassword: "¿Olvidaste tu contraseña?",
        login: "Iniciar sesi\u00F3n",
        loginTitle: "Iniciar sesi\u00F3n en Getaway",
        loginDescription: "Experiencias nuevas todos los d\u00EDas",
        newUser: "¿Eres nuevo en Getaway?",
        password: "Contraseña",
        confirmPassword: "Confirmar contraseña",
        profile: "Mi perfil",
        experiences: "Mis experiencias",
        favourites: "Mis favoritos",
        reviews: "Mis reseñas",
        rememberMe: "Recuerdame",
        logout: "Cerrar sesi\u00F3n",
        resetPasswordTitle: "Ingresa tu email y recibe un enlace de recuperaci\u00F3n",
        resetPasswordButton: "Enviar",
        createAccountPopUp: "Crea tu cuenta",
        createAccountDescription: "Ingresa tus datos para comenzar a ofrecer tus experiencias y hacer reseñas",
        max: "(M\u00E1ximo {{num}})",
        name: "Nombre",
        surname: "Apellido",
        createButton: "Crear cuenta",
        emailPlaceholder: "juan@ejemplo.com",
        namePlaceholder: "Juan",
        surnamePlaceholder: "Martínez",
        passwordPlaceholder: "Entre 8 y 25 caracteres",
        editAccountPopUp: "Edit\u00E1 tu cuenta",
        editPassword: "Nueva contraseña",
        confirmEditPassword: "Confirmar nueva contraseña",
        editAccount:"Editar cuenta"
    },

    Filters: {
        title: "Filtros",
        city: {
            field: "Ciudad",
            placeholder: "¿A dónde?",
        },
        price: {
            title: "Precio",
            min: "0",
        },
        scoreAssign: "Puntaje",
        btn: {
            submit: "Buscar",
            clear: "Limpiar filtros",
        },
    },

    Experience: {
        name: "Nombre",
        category: "Categor\u00EDa",
        price: {
            name: "Precio",
            null: "Precio no listado",
            free: "Gratis",
            exist: "${{price}}",
        },
        information: "Descripci\u00F3n",
        mail: {
            field: "Email",
            placeholder: "juanmartinez@ejemplo.com",
        },
        url: {
            field: "Url",
            placeholder: "https://google.com",
        },
        country: "País",
        city: "Ciudad",
        address: "Direcci\u00F3n",
        image: "Imagen",
        placeholder: "Escribe para buscar",
        reviews: "Reseñas {{reviewCount}}",
        notVisible: "La experiencia est\u00E1 oculta en este momento",
    },

    ExperienceDetail: {
        imageDefault: "Esta imagen no se corresponde con la experiencia",
        price: {
            null: "Precio no listado",
            free: "Gratis",
            exist: "${{price}}",
        },
        description: "Descripci\u00F3n",
        noData: "Informaci\u00F3n no brindada",
        url: "Sitio oficial",
        email: "Email",
        review: "Reseñas",
        writeReview: "Escribir Reseña",
        notVisible: "La experiencia est\u00E1 oculta en este momento",
        noReviews: "Esta experiencia no tiene rese\u00F1as a\u00FAn. S\u00E9 el primero en realizar una!"
    },

    ExperienceForm: {
        title: "Crea tu experiencia",
        error: {
            name: {
                isRequired: "Este campo no puede estar vac\u00EDo",
                pattern: "El nombre ingresado no posee un formato v\u00E1lido00EDo",
                length: "El nombre de la experiencia debe tener entre 3-50 caracteres",
            },
            category: {
                isRequired: "Este campo no puede estar vac\u00EDo",
            },
            price: {
                max: "El precio debe ser menor a $9999999",
            },
            description: {
                pattern: "La descripci\u00F3n ingresada no posee un formato v\u00E1lido",
                isRequired: "Este campo no puede estar vac\u00EDo",
                length: "La descripci\u00F3n debe tener un m\u00E1ximo de 500 caracteres",
            },
            mail: {
                pattern: "El email ingresado no es v\u00E1lido",
                isRequired: "Este campo no puede estar vac\u00EDo",
                length: "El mail debe tener un m\u00E1ximo de 255 caracteres",
            },
            url: {
                pattern: "La URL ingresada no es v\u00E1lido",
                length: "La URL debe tener un m\u00E1ximo de 500 caracteres",
            },
            country: {
                isRequired: "Este campo no puede estar vac\u00EDo",
            },
            city: {
                isRequired: "Este campo no puede estar vac\u00EDo",
            },
            address: {
                pattern: "La direcci\u00F3n ingresada no es v\u00E1lido",
                isRequired: "Este campo no puede estar vac\u00EDo",
                length: "La direcci\u00F3n debe tener entre 5-100 caracteres",
            },
        }
    },

    User: {
        profile: {
            description: "Mi perfil",
            name: "Nombre: {{userName}}",
            surname: "Apellido: {{userSurname}}",
            email: "Email: {{userEmail}}",
            editBtn: "Editar perfil",
            verifyAccountBtn: "Verifica tu cuenta",
        },
        experiences: {
            title: "Mis experiencias",
            category: "Categor\u00EDa",
            score: "Puntaje",
            price: "Precio",
            views: "Vistas",
            actions: "Acciones",
            reviewsCount: "Reseñas {{count}}",
            deleteTitle: "Eliminar experiencia",
            confirmDelete: "¿Est\u00E1 seguro que desea eliminar la experiencia: {{experienceName}}?"
        },
        noExperiences: "Aún no has creado ninguna experiencia",
        experiencesTitle: "Mis experiencias",
        noFavs: "Aún no has agregado experiencias a favoritos",
        favsTitle: "Mis favoritos",
        noReviews: "Aún no has escrito ninguna reseña",
        reviewsTitle: "Mis reseñas",
        error:{
            image: {
                isRequired: "Este campo no puede estar vac\u00EDo",
                size: "La imagen es muy grande",
            },
        },
        imgTitle: "Selecciona una imagen de perfil",
    },

    EmptyResult: "Parece que no hay ninguna experiencia que coincida con tu búsqueda",

    Button: {
        cancel: "Cancelar",
        create: "Guardar",
        confirm: "Confirmar",
    },

    Input: {
        optional: "(Opcional)",
        maxValue: "(M\u00E1ximo {{value}})",
    },

    Copyright: "Getaway Copyright © {{year}} - Todos los derechos reservados",

    404: {
        title: "Error 404",
        description: "Parece que la página que estas buscando no existe",
    },

    Error: {
        whoops: "Whoops!",
        backBtn: "Inicio",
        title: "Error {{errorCode}}",
    },

    Pagination: {
        message: "Página {{currentPage}} de {{maxPage}}",
        alt: {
            nextPage: "Siguiente",
            beforePage: "Anterior",
        },
    },

    Order: {
        title: "Ordenar por: ",
        OrderByRankAsc: "Puntaje ascendente",
        OrderByRankDesc: "Puntaje descendente",
        OrderByAZ: "A-Z",
        OrderByZA: "Z-A",
        OrderByLowPrice: "Menor precio",
        OrderByHighPrice: "Mayor precio",
        OrderByViewAsc: "Menos vistas",
        OrderByViewDesc: "M\u00E1s vistas",
    },

    Landing: {
        user: {
            viewed: "\u00DAltimas visitadas",
            recommendedByFavs: "Basado en tus favoritos",
            recommendedByReviews: "Basado en tus rese\u00F1as",
        },
        anonymous: {
            aventura: "Mejores valoradas de aventura",
            gastronomia: "Mejores valoradas de gastronom\u00EDa",
            hoteleria: "Mejores valoradas de hoteler\u00EDa",
            relax: "Mejores valoradas de relax",
            vida_nocturna: "Mejores valoradas de vida nocturna",
            historico: "Mejores valoradas de hist\u00F3rico",
        },
    },

    CreateAccount: {
        error:{
            email: {
                isRequired: "Este campo es obligatorio",
                length: "El mail debe tener un m\u00E1ximo de 255 caracteres",
                pattern: "El email ingresado no es v\u00E1lido",
            },
            name: {
                isRequired: "Este campo es obligatorio",
                length: "El nombre debe tener un m\u00E1ximo de 50 caracteres",
                pattern: "El nombre ingresado no es v\u00E1lido",
            },
            surname: {
                isRequired: "Este campo es obligatorio",
                length: "El apellido debe tener un m\u00E1ximo de 50 caracteres",
                pattern: "El apellido ingresado no es v\u00E1lido",
            },
            password: {
                isRequired: "Este campo es obligatorio",
                length: "La contraseña debe tener entre 8-25 caracteres",
                pattern: "La contraseña ingresado no es v\u00E1lido",
            },
            passwordsMustMatch:"Las contraseñas no coinciden",
        }
    },

    Experiences: {
        search: {
            search:"Buscando ",
            category: "en ",
            name: " \" {{name}} \""
        }
    },

    Review: {
        title: "Título",
        description: "Descripci\u00F3n",
        score: "Puntaje",
    },

    ReviewForm:{
        title: "Escribe una reseña para {{experienceName}}",
        editTitle:"Edita la reseña para {{experienceName}}",
        error: {
            title: {
                pattern: "El titulo ingresado no posee un formato v\u00E1lido",
                isRequired: "Este campo no puede estar vac\u00EDo",
                length: "El titulo debe tener entre 3-50 caracteres",
            },
            description: {
                pattern: "La descripci\u00F3n ingresada no posee un formato v\u00E1lido",
                isRequired: "Este campo no puede estar vac\u00EDo",
                length: "La descripci\u00F3n debe tener entre 3-255 caracteres",
            },
            score: {
                pattern: "aaaa",
                isRequired: "Este campo no puede estar vac\u00EDo",
            },
        }
    }
};