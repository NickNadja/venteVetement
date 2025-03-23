
// Obtenir le contexte du canvas
const ctx = document.getElementById('myChart').getContext('2d');

// Créer le graphique
const myChart = new Chart(ctx, {
type: 'bar', // Type de graphique
data: {
    labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai'],
    datasets: [{
        label: 'Ventes mensuelles',
        data: [12, 19, 3, 5, 2],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)'
        ],
        borderWidth: 1
    }]
},
options: {
    scales: {
        y: {
            beginAtZero: true
        }
    }
}
});




// Récupérer le contexte du canvas
const ctx1 = document.getElementById('lineChart').getContext('2d');

// Créer le graphique
const lineChart = new Chart(ctx1, {
    type: 'line', // Type de graphique : ligne
    data: {
        labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin'], // Étiquettes de l'axe X
        datasets: [{
            label: 'Ventes mensuelles',
            data: [12, 19, 3, 5, 2, 3], // Données
            borderColor: 'rgb(75, 192, 192)', // Couleur de la ligne
            tension: 0.1, // Courbure de la ligne (0 = droite, 1 = très courbée)
            fill: false // Ne pas remplir sous la ligne
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true // Commencer l'axe Y à 0
            }
        }
    }
});


// Récupérer le contexte du canvas
const ctx2 = document.getElementById('myPieChart').getContext('2d');

// Créer le graphique en secteurs
const myPieChart = new Chart(ctx2, {
    type: 'pie', // Type de graphique : pie
    data: {
        labels: ['Produits A', 'Produits B', 'Produits C', 'Produits D'], // Étiquettes des secteurs
        datasets: [{
            data: [30, 20, 25, 25], // Valeurs (en pourcentage ou unités)
            backgroundColor: [ // Couleurs de chaque secteur
                'rgb(255, 99, 132)',  // Rouge
                'rgb(54, 162, 235)',  // Bleu
                'rgb(255, 205, 86)',  // Jaune
                'rgb(75, 192, 192)'   // Turquoise
            ],
            borderWidth: 1 // Largeur de la bordure entre les secteurs
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'bottom', // Position de la légende
            },
            title: {
                display: true,
                text: 'Répartition des ventes par produit'
            }
        }
    }
});