const menu = document.querySelector('#mobile-menu');
const menuLinks = document.querySelector('.navbar__menu');

menu.addEventListener('click', function() {
    menu.classList.toggle('is-active');
    menuLinks.classList.toggle('active');
});

// UNIT
$(document).ready(function() {
    function loadAvailableUnits() {
        $.ajax({
            url: '/getAvailableUnits',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                const availableUnitsList = $('#available-units');
                availableUnitsList.empty();

                const unitTypeOrder = ['Lords', 'Hero', 'Core', 'Special', 'Rare'];
                const unitsByType = {};

                unitTypeOrder.forEach(type => {
                    unitsByType[type] = [];
                });

                data.forEach(function(unit) {
                    if (unitsByType[unit.unitType]) {
                        unitsByType[unit.unitType].push(unit);
                    }
                });

                unitTypeOrder.forEach(function(type) {
                    if (unitsByType[type].length > 0) {
                        availableUnitsList.append('<h3>' + type + ':</h3>');

                        unitsByType[type].forEach(function(unit) {
                            const listItem = $('<li>')
                                .text(unit.name + ' (' + unit.pointsCostPerUnit + ' pts)')
                                .attr('data-nation', unit.nation);

                            const addButton = $('<button>').text('Add')
                                .click(function() {
                                    $.post('/addUnit', { unitId: unit.id })
                                        .done(function() {
                                            loadSelectedUnits();
                                        })
                                        .fail(function(xhr) {
                                            alert("Error: " + xhr.responseText);
                                        });
                                });

                            availableUnitsList.append(listItem.append(addButton));
                        });
                    }
                });
            },
            error: function(error) {
                console.error('Error fetching available units:', error);
            }
        });
    }


    loadAvailableUnits();



    // SELECTED UNIT
    function loadSelectedUnits() {
        $.ajax({
            url: '/getSelectedUnits',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                const selectedUnitsList = $('.selectedUnits-container ul');
                selectedUnitsList.empty();
                $.each(data, function(index, unit) {
                    const listItem = $('<li>').attr('data-unit-id', unit.id);
                    listItem.append($('<span>').addClass('unit-name').text('  ' + unit.name + '     '));

                    const decreaseButton = $('<button>').text(' - ')
                        .addClass('decrease-unit')
                        .data('id', unit.id)
                        .click(function() {
                            $.post('/decreaseUnitQuantity', { id: unit.id })
                                .done(function() {
                                    loadSelectedUnits();
                                })
                                .fail(function(xhr) {
                                    alert("Error: " + xhr.responseText);
                                });
                        });

                    const increaseButton = $('<button>').text(' + ')
                        .addClass('increase-unit')
                        .data('id', unit.id)
                        .click(function() {
                            $.post('/increaseUnitQuantity', { id: unit.id })
                                .done(function() {
                                    loadSelectedUnits();
                                })
                                .fail(function(xhr) {
                                    alert("Error: " + xhr.responseText);
                                });
                        });

                    const removeButton = $('<button>').text('Remove')
                        .addClass('remove-unit')
                        .data('id', unit.id)
                        .click(function() {
                            $.post('/removeUnit', { id: unit.id })
                                .done(function() {
                                    loadSelectedUnits();
                                })
                                .fail(function(xhr) {
                                    alert("Error: " + xhr.responseText);
                                });
                        });

                    const selectButton = $('<button>').text('Upgrade')
                        .addClass('select-upgrade-button')
                        .click(function() {
                            let unitId = unit.id;
                            $.ajax({
                                type: 'GET',
                                url: '/units/' + unitId + '/upgrades',
                                success: function(upgradesData) {
                                    const upgradesList = $('#unitsUpgrades-container');
                                    upgradesList.empty();
                                    upgradesData.forEach(function(upgrade) {
                                        let upgradeItem = $('<div>').text(upgrade.name + ' (' + upgrade.pointsCost + ' pts)');

                                        if (upgrade.selected) {
                                            upgradeItem.prepend('<img src="/selectedUpgrade.png" alt="selected" class="upgrade-image" />');
                                        }

                                        let addUpgradeButton = $('<button>')
                                            .text(upgrade.selected ? 'Selected' : 'Add')
                                            .data('upgrade-id', upgrade.id)
                                            .prop('disabled', upgrade.selected)
                                            .click(function() {
                                                if (!upgrade.selected) {
                                                    $.post('/addUpgrade', { upgradeId: upgrade.id })
                                                        .done(function() {
                                                            upgradeItem.prepend('<img src="/selectedUpgrade.png" alt="selected" class="upgrade-image" />');
                                                            loadSelectedUnits();

                                                        })
                                                        .fail(function(xhr) {
                                                            alert("Error: " + xhr.responseText);
                                                        });
                                                }
                                            });

                                        let removeUpgradeButton = $('<button>')
                                            .text('Remove')
                                            .data('upgrade-id', upgrade.id)
                                            .click(function() {
                                                $.post('/removeSelectedUpgrade', { upgradeId: upgrade.id })
                                                    .done(function() {
                                                        upgradeItem.find('img.upgrade-image').remove();
                                                        loadSelectedUnits();
                                                    })
                                                    .fail(function(xhr) {
                                                        alert("Error: " + xhr.responseText);
                                                    });
                                            });

                                        upgradeItem.append(addUpgradeButton).append(removeUpgradeButton);
                                        upgradesList.append(upgradeItem);
                                    });
                                },
                                error: function() {
                                    alert('Error fetching upgrades.');
                                }
                            });
                        });

                    listItem.append(decreaseButton);
                    listItem.append($('<span>').addClass('unit-quantity').text('  ' + unit.quantity + '  '));
                    listItem.append(increaseButton, removeButton, selectButton);
                    selectedUnitsList.append(listItem);
                });
            },
            error: function(error) {
                console.error('Error fetching selected units:', error);
            }
        });
    }


    loadAvailableUnits();
    loadSelectedUnits();

    // Filtracja jednostek według nacji
    function filterUnitsByNation(nation) {
        $('#available-units li').each(function() {
            const unitNation = $(this).data('nation');
            if (unitNation === nation || nation === 'All') {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }


    filterUnitsByNation('All');
    $('#nationSelect').on('change', function() {
        const selectedNation = $(this).val();
        filterUnitsByNation(selectedNation);
    });

});